
package com.github.microwww.security.cli.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * 于service 中的 Emloye 等价
 *
 * @author changshu.li
 */
public class Employee implements Serializable {

    private final String cacheKey;
    private String account;
    private String name;
    private Object other;

    public Employee() {
        cacheKey = UUID.randomUUID().toString();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String id) {
        this.account = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }
}
