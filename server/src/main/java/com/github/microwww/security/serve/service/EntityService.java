package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.vo.EntityEnum;
import com.github.microwww.security.serve.vo.ID;
import com.github.microwww.security.serve.vo.Pagers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

@Service
@Transactional
public class EntityService implements ApplicationContextAware {

    private List<PersistFilter> filters;
    @PersistenceContext
    EntityManager entityManager;

    public Page<?> listEntity(Pagers data, EntityEnum entity) {
        PageRequest rq = PageRequest.of(data.getPage(), data.getSize());
        CriteriaBuilder cr = entityManager.getCriteriaBuilder();
        long count;
        {
            CriteriaQuery<Object> query = cr.createQuery();
            Root<?> root = query.from(entity.getEntity());
            Path<Object> id = root.get(entity.getPrimaryKey());
            query.select(cr.count(id).alias("ct"));
            count = (Long) entityManager.createQuery(query).getSingleResult();
        }
        CriteriaQuery<Object> query = cr.createQuery();
        Root<?> root = query.from(entity.getEntity());
        query.select(root);
        Optional<Pagers.RequestSort> sort = data.trySort();
        if (sort.isPresent()) {
            Pagers.RequestSort s = sort.get();
            if (s.order.startsWith("asc")) {
                query.orderBy(cr.asc(root.get(s.field)));
            } else if (s.order.startsWith("desc")) {
                query.orderBy(cr.desc(root.get(s.field)));
            }
        }
        if (data.getQuery() != null) {
            List<Predicate> where = new ArrayList<>();
            data.getQuery().forEach((k, v) -> {
                Pagers.Query parse = Pagers.Query.parse(k, v);
                Predicate done = parse.tryOpt().done(cr, parse.key2path(root), parse);
                where.add(done);
            });
            query.where(where.toArray(new Predicate[where.size()]));
        }
        Assert.isTrue(rq.getOffset() <= Integer.MAX_VALUE, "data overflow INTEGER.MAX_VALUE");
        List<?> list = entityManager.createQuery(query).setFirstResult((int) rq.getOffset()).setMaxResults(rq.getPageSize()).getResultList();
        PageImpl pg = new PageImpl(list, rq, count);
        return pg;
    }

    public <T> T saveEntity(ID form, Class<T> t) {
        try {
            return this.trySaveEntity(form, t);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T trySaveEntity(ID form, Class<T> en) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        Ref<T> warp = new Ref<>();
        Integer id = form.getId();
        if (id != null) {
            warp.setRef(entityManager.find(en, id));
        }
        if (warp.ref == null) {
            warp.setRef(en.getConstructor().newInstance());
            warp.setCreate(true);
        }

        filters.stream().filter(e -> e.isSupport(form.getClass(), en)).forEach(e -> {
            e.beforeSave(form, warp.getRef());
        });

        warp.copyFromIgnoreNull(form, "createTime");

        // 设置 warp class
        Field[] fields = form.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (ID.class.isAssignableFrom(f.getType())) {
                Object formValue = f.get(form);
                if (formValue != null) {
                    Integer val = ((ID) formValue).getId();
                    if (val != null) {
                        Field dec = warp.getRef().getClass().getDeclaredField(f.getName());
                        Object oo = entityManager.find(dec.getType(), val);
                        dec.setAccessible(true);
                        dec.set(warp.getRef(), oo);
                    }
                }
            }
        }

        if (warp.isCreate()) {
            entityManager.persist(warp.getRef());
        } else {
            entityManager.merge(warp.getRef());
        }

        filters.stream().filter(e -> e.isSupport(form.getClass(), en)).forEach(e -> {
            e.afterSave(form, warp.getRef());
        });
        return warp.getRef();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Collection<PersistFilter> values = applicationContext.getBeansOfType(PersistFilter.class).values();
        filters = Collections.unmodifiableList(new ArrayList<>(values));
    }

    private static class Ref<T> {
        private boolean create;
        T ref;

        public boolean isCreate() {
            return create;
        }

        public void setCreate(boolean create) {
            this.create = create;
        }

        public T getRef() {
            return ref;
        }

        public void setRef(T ref) {
            this.ref = ref;
        }

        public void copyFromIgnoreNull(Object source, String... ignoreProperties) {
            copyPropertiesIgnoreNull(source, ref);
        }

        public void copyToIgnoreNull(Object target, String... ignoreProperties) {
            copyPropertiesIgnoreNull(ref, target);
        }

        public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties) throws BeansException {
            Assert.notNull(source, "Source must not be null");
            Assert.notNull(target, "Target must not be null");

            Class<?> actualEditable = target.getClass();
            PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
            List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

            for (PropertyDescriptor targetPd : targetPds) {
                Method writeMethod = targetPd.getWriteMethod();
                if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                    PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                    if (sourcePd != null) {
                        Method readMethod = sourcePd.getReadMethod();
                        if (readMethod != null &&
                                ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                            try {
                                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                    readMethod.setAccessible(true);
                                }
                                Object value = readMethod.invoke(source);
                                if (value == null) {// ignore NULL
                                    continue;
                                }
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            } catch (Throwable ex) {
                                throw new FatalBeanException(
                                        "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                            }
                        }
                    }
                }
            }
        }
    }
}
