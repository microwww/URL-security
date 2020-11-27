package com.github.microwww.security.serve.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface PersistFilter<T, U> {

    public default boolean isSupport(Class form, Class entity) {
        Type[] types = this.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parm = (ParameterizedType) type;
                if (PersistFilter.class.equals(parm.getRawType())) {
                    Type[] act = parm.getActualTypeArguments();
                    if (act.length == 2 && act[1] instanceof Class) {
                        return act[1].equals(entity);
                    }
                }
            }
        }
        return false;
    }

    public void beforeSave(T form, U entity);

    public void afterSave(T form, U entity);
}
