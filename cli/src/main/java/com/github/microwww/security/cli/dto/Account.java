
package com.github.microwww.security.cli.dto;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {

    private final String cacheKey;
    private String account;
    private String name;

    public Account() {
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

    public String getCacheKey() {
        return this.cacheKey;
    }
}
