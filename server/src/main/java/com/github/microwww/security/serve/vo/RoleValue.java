package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class RoleValue {

    public static class Simple extends AbstractDomainValue<Role> {

        public Simple(Role domain) {
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
    }

    public static class Info extends Simple {

        public Info(Role domain) {
            super(domain);
        }
    }

    public static class More extends Info {

        public More(Role domain) {
            super(domain);
        }

        public List<RoleAccountValue.Simple> getRoleAccounts() {
            List<RoleAccount> list = super.domain.getRoleAccounts();
            return list.stream().map(RoleAccountValue.Simple::new).collect(Collectors.toList());
        }

        public List<RoleAuthorityValue.Simple> getRoleAuthoritys() {
            List<RoleAuthority> list = super.domain.getRoleAuthoritys();
            return list.stream().map(RoleAuthorityValue.Simple::new).collect(Collectors.toList());
        }

    }
}