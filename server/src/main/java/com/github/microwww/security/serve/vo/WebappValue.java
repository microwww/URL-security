package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class WebappValue {

    public static class Simple extends AbstractDomainValue<Webapp> {

        public Simple(Webapp domain) {
            super(domain);
        }

        public String getAppId() {
            return super.domain.getAppId();
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

        public Info(Webapp domain) {
            super(domain);
        }

        public RoleValue.Simple getRole() {
            return new RoleValue.Simple(super.domain.getRole());
        }
    }

    public static class More extends Info {

        public More(Webapp domain) {
            super(domain);
        }

        public List<AuthorityValue.Simple> getAuthoritys() {
            List<Authority> list = super.domain.getAuthoritys();
            return list.stream().map(AuthorityValue.Simple::new).collect(Collectors.toList());
        }
    }
}
