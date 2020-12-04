package com.github.microwww.security.serve.domain;

import java.util.*;
import javax.persistence.*;

/**
 * Auto-generated by:
 * org.apache.openjpa.jdbc.meta.ReverseMappingTool$AnnotatedCodeGenerator
 */
@Entity
@Table(name = "permission")
public class Authority extends AbstractBasicEntity {

    public enum Type {
        URL, MENU;
    }

    @Basic
    @Column(length = 45)
    private String name;

    @Basic
    @Column(nullable = false)
    private String uri;

    @Basic
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Basic
    @Column(columnDefinition = "INT")
    private int sort;

    @Basic
    private String description;

    @Basic
    @Column(name = "create_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "parent_id", columnDefinition = "INT")
    private Authority parent;

    @OneToMany(targetEntity = Authority.class, mappedBy = "parent", cascade = CascadeType.MERGE)
    private List<Authority> authoritys;

    @OneToMany(targetEntity = RoleAuthority.class, mappedBy = "authority", cascade = CascadeType.MERGE)
    private List<RoleAuthority> roleAuthoritys;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "webapp_id", columnDefinition = "INT", nullable = false)
    private Webapp webapp;

    public Authority() {
    }

    public Authority getParent() {
        return parent;
    }

    public void setParent(Authority authority) {
        this.parent = authority;
    }

    public List<Authority> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(List<Authority> authoritys) {
        this.authoritys = authoritys;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoleAuthority> getRoleAuthoritys() {
        return roleAuthoritys;
    }

    public void setRoleAuthoritys(List<RoleAuthority> roleAuthoritys) {
        this.roleAuthoritys = roleAuthoritys;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Webapp getWebapp() {
        return webapp;
    }

    public void setWebapp(Webapp webapp) {
        this.webapp = webapp;
    }
}
