package com.github.microwww.security.serve.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Auto-generated by:
 * org.apache.openjpa.jdbc.meta.ReverseMappingTool$AnnotatedCodeGenerator
 */
@Entity
@Table(name = "account")
public class Account extends AbstractBasicEntity {
    private static final long serialVersionUID = 1L;

    @Basic
    @Column(nullable = false, length = 45)
    private String account;

    @Basic
    private boolean disable = false;

    @Basic
    @Column(length = 45)
    private String name;

    @Basic
    @Column(length = 45)
    private String phone;

    @Basic
    private String email;

    @OneToMany(targetEntity = RoleAccount.class, mappedBy = "account", cascade = CascadeType.MERGE)
    private List<RoleAccount> roleAccounts;

    public Account() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<RoleAccount> getRoleAccounts() {
        return roleAccounts;
    }

    public void setRoleAccounts(List<RoleAccount> roleAccounts) {
        this.roleAccounts = roleAccounts;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
