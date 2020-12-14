package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.AbstractBasicEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum EntityEnum {
    Account,
    Permission,
    Role,
    RoleAccount,
    RolePermission,
    Webapp;

    private static final String PREFIX = com.github.microwww.security.serve.domain.Webapp.class.getPackage().getName();
    private static final String VO_PREFIX = AbstractDomainValue.class.getPackage().getName();

    public <T extends AbstractBasicEntity> Class<T> getEntity() {
        try {
            return (Class<T>) Class.forName(PREFIX + "." + this.name());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String[] tryType = {"Info", "Simple"};

    public <T, U> U map(T val) {
        return map(val, tryType);
    }

    public <T, U> U map(T val, String[] tryType) {
        for (String type : tryType) {
            try {
                Constructor<?>[] cst = Class.forName(VO_PREFIX + "." + this.name() + "Value$" + type).getConstructors();
                for (Constructor<?> cs : cst) {
                    Class<?>[] pms = cs.getParameterTypes();
                    if (pms.length == 1) {
                        if (pms[0].isInstance(val)) {
                            try {
                                return (U) cs.newInstance(val);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {// ignore
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {// ignore
            }
        }
        throw new RuntimeException("Not find Constructor : " + tryType);
    }

    public String getPrimaryKey() {
        return "id";
    }
}