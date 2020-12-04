package com.github.microwww.security.serve.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "permission_account")
public class AuthAccount extends AbstractBasicEntity {
    private static final long serialVersionUID = 1L;

    public enum Type {
        PASSWORD
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id", columnDefinition = "INT")
    private Account account;

    @Enumerated(EnumType.STRING)
    private Type type = Type.PASSWORD;

    private String key;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime;

    public AuthAccount() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
