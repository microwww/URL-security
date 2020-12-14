package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.Authority;
import com.github.microwww.security.serve.domain.RoleAuthority;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

        public AuthorityValue.Simple getParent() {
            return Optional.ofNullable(super.domain.getParent()).map(AuthorityValue.Simple::new).orElse(null);
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

    public static class Form extends ID {
        private String name;
        private String uri;
        private Authority.Type type;
        private int sort;
        private String description;
        private AuthorityValue.Form parent;
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

        public Authority.Type getType() {
            return type;
        }

        public void setType(Authority.Type type) {
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
