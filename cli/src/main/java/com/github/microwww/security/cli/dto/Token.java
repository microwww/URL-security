package com.github.microwww.security.cli.dto;

import java.io.Serializable;

public class Token implements Serializable {
    transient private final long createTime;
    private String token;
    private int expSeconds; //最终过期时间

    public Token() {
        createTime = System.currentTimeMillis();
    }

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public int getExpSeconds() {
        return expSeconds;
    }

    public boolean isExpired() {
        return createTime + (expSeconds * 1000) < System.currentTimeMillis();
    }

    public Token setExpSeconds(int expSeconds) {
        this.expSeconds = expSeconds;
        return this;
    }
}
