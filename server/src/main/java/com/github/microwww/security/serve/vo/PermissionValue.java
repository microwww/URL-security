package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.Permission;
import com.github.microwww.security.serve.domain.RolePermission;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PermissionValue {

    public static class Simple extends AbstractDomainValue<Permission> {

        public Simple(Permission domain) {
            super(domain);
        }

        public Date getCreateTime() {
            return super.domain.getCreateTime();
        }

        public String getDescription() {
            return super.domain.getDescription();
        }

        public int getId() {
            return super.domain.getId();
        }

        public String getName() {
            return super.domain.getName();
        }

        public int getSort() {
            return super.domain.getSort();
        }

        public String getType() {
            return super.domain.getType().name();
        }

        public String getUri() {
            return super.domain.getUri();
        }
    }

    public static class Info extends Simple {

        public Info(Permission domain) {
            super(domain);
        }

        public PermissionValue.Simple getParent() {
            return Optional.ofNullable(super.domain.getParent()).map(PermissionValue.Simple::new).orElse(null);
        }

        public WebappValue.Simple getWebapp() {
            return new WebappValue.Simple(super.domain.getWebapp());
        }
    }

    public static class More extends Info {

        public More(Permission domain) {
            super(domain);
        }

        public List<PermissionValue.Simple> getPermissions() {
            List<Permission> list = super.domain.getPermissions();
            return list.stream().map(PermissionValue.Simple::new).collect(Collectors.toList());
        }

        public List<RolePermissionValue.Simple> getRolePermissions() {
            List<RolePermission> list = super.domain.getRolePermissions();
            return list.stream().map(RolePermissionValue.Simple::new).collect(Collectors.toList());
        }
    }

    public static class Form extends ID {
        private String name;
        private String uri;
        private Permission.Type type;
        private int sort;
        private String description;
        private PermissionValue.Form parent;
        private WebappValue.Form webapp;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Permission.Type getType() {
            return type;
        }

        public void setType(Permission.Type type) {
            this.type = type;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Form getParent() {
            return parent;
        }

        public void setParent(Form parent) {
            this.parent = parent;
        }

        public WebappValue.Form getWebapp() {
            return webapp;
        }

        public void setWebapp(WebappValue.Form webapp) {
            this.webapp = webapp;
        }
    }
}
