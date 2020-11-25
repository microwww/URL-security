package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AuthorityValue {

    public static class Simple extends AbstractDomainValue<Authority> {

        public Simple(Authority domain) {
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

        public Info(Authority domain) {
            super(domain);
        }

        public AuthorityValue.Simple getAuthority() {
            return new AuthorityValue.Simple(super.domain.getAuthority());
        }

        public WebappValue.Simple getWebapp() {
            return new WebappValue.Simple(super.domain.getWebapp());
        }
    }

    public static class More extends Info {

        public More(Authority domain) {
            super(domain);
        }

        public List<AuthorityValue.Simple> getAuthoritys() {
            List<Authority> list = super.domain.getAuthoritys();
            return list.stream().map(AuthorityValue.Simple::new).collect(Collectors.toList());
        }

        public List<RoleAuthorityValue.Simple> getRoleAuthoritys() {
            List<RoleAuthority> list = super.domain.getRoleAuthoritys();
            return list.stream().map(RoleAuthorityValue.Simple::new).collect(Collectors.toList());
        }
    }
}
