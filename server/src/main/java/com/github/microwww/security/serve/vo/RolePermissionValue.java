package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.RolePermission;

import java.util.Date;

public abstract class RolePermissionValue {

    public static class Simple extends AbstractDomainValue<RolePermission> {

        public Simple(RolePermission domain) {
            super(domain);
        }

        public Date getCreateTime() {
            return super.domain.getCreateTime();
        }

        public int getId() {
            return super.domain.getId();
        }
    }

    public static class Info extends Simple {

        public Info(RolePermission domain) {
            super(domain);
        }

        public PermissionValue.Simple getPermission() {
            return new PermissionValue.Simple(super.domain.getPermission());
        }

        public RoleValue.Simple getRole() {
            return new RoleValue.Simple(super.domain.getRole());
        }
    }

    public static class More extends Info {

        public More(RolePermission domain) {
            super(domain);
        }
    }

    public static class Form extends ID {
        private PermissionValue.Form permission;
        private RoleValue.Form role;

        public PermissionValue.Form getPermission() {
            return permission;
        }

        public void setPermission(PermissionValue.Form permission) {
            this.permission = permission;
        }

        public RoleValue.Form getRole() {
            return role;
        }

        public void setRole(RoleValue.Form role) {
            this.role = role;
        }
    }
}
