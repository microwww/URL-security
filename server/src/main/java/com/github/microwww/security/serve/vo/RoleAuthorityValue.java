package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.RoleAuthority;

import java.util.Date;

public abstract class RoleAuthorityValue {

    public static class Simple extends AbstractDomainValue<RoleAuthority> {

        public Simple(RoleAuthority domain) {
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

        public Info(RoleAuthority domain) {
            super(domain);
        }

        public AuthorityValue.Simple getAuthority() {
            return new AuthorityValue.Simple(super.domain.getAuthority());
        }

        public RoleValue.Simple getRole() {
            return new RoleValue.Simple(super.domain.getRole());
        }
    }

    public static class More extends Info {

        public More(RoleAuthority domain) {
            super(domain);
        }
    }

    public static class Form extends ID {
        private AuthorityValue.Form authority;
        private RoleValue.Form role;

        public AuthorityValue.Form getAuthority() {
            return authority;
        }

        public void setAuthority(AuthorityValue.Form authority) {
            this.authority = authority;
        }

        public RoleValue.Form getRole() {
            return role;
        }

        public void setRole(RoleValue.Form role) {
            this.role = role;
        }
    }
}
