package com.github.microwww.security.serve.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class Pagers {
    @ApiModelProperty("支持的格式: id / id ascend / id descend")
    private String sort;
    @ApiModelProperty(value = "from 0 , default = 0", example = "0")
    private int page = 0;
    @ApiModelProperty("default = 20")
    private int size = 20;
    @ApiModelProperty(required = true)
    private EntityEnum entity;
    @ApiModelProperty("支持的格式: {id: 1}, id: {key: 'id', val: '', opt: '[like, equal]'} ")
    private Map<String, Object> query;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public EntityEnum getEntity() {
        return entity;
    }

    public void setEntity(EntityEnum entity) {
        this.entity = entity;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public Optional<RequestSort> trySort() {
        if (this.sort == null) {
            return Optional.empty();
        }
        return Optional.of(new RequestSort(this.sort));
    }

    public class RequestSort {
        public final String order;
        public final String field;

        public RequestSort(String sort) {
            String[] od = sort.split(" ");
            Assert.isTrue(od.length <= 2, "sort 格式错误, 仅允许三种格式: `status`, `status ascend`, `status descend`");
            this.field = od[0];
            this.order = od.length == 2 ? od[1].toLowerCase() : "ascend";
        }
    }

    public static class Query {
        String key;
        Object val;
        String opt;

        public static Query parse(String key, Object val) {
            Query qr = new Query();
            qr.setKey(key);
            if (val instanceof Map) {
                Map map = (Map) val;
                Object k = map.get("key");
                if (k != null) {
                    qr.setKey(k.toString());
                }
                qr.setVal(map.get("val"));
                Object opt = map.get("opt");
                if (opt != null) {
                    qr.setOpt(opt.toString());
                }
            } else {
                qr.setVal(val);
            }
            return qr;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getVal() {
            return val;
        }

        public void setVal(Object val) {
            this.val = val;
        }

        public String getOpt() {
            return opt;
        }

        public void setOpt(String opt) {
            this.opt = opt;
        }

        public Operation tryOpt() {
            return Operation.parse(this.opt);
        }

        public Path key2path(Root root) {
            Assert.isTrue(key != null, "key not NULL !");
            String[] ks = {this.key};
            if (key.contains(".")) {
                ks = key.split(Pattern.quote("."));
            }
            Path path = root.get(ks[0]);
            for (int i = 1; i < ks.length; i++) {
                path = path.get(ks[i]);
            }
            return path;
        }
    }

    public enum Operation {
        EQUAL {
            @Override
            public Predicate done(CriteriaBuilder builder, Path path, Query query) {
                return builder.equal(path, query.getVal());
            }
        },
        LIKE {
            @Override
            public Predicate done(CriteriaBuilder builder, Path path, Query query) {
                return builder.like(path, query.getVal().toString());
            }
        },
        NULL {
            @Override
            public Predicate done(CriteriaBuilder builder, Path path, Query query) {
                return builder.isNull(path);
            }
        },
        NOT_NULL {
            @Override
            public Predicate done(CriteriaBuilder builder, Path path, Query query) {
                return builder.isNotNull(path);
            }
        };

        public abstract Predicate done(CriteriaBuilder builder, Path path, Query query);

        public static Operation parse(String operation) {
            if (operation == null) {
                return Operation.EQUAL;
            }
            return Operation.valueOf(operation.toUpperCase());
        }
    }
}
