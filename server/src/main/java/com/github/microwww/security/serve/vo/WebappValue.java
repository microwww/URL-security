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
            return Optional.ofNullable(super.domain.getRole()).map(RoleValue.Simple::new).orElse(null);
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

    public static class Form extends ID {
        private String appId;
        private String appSecurity;
        private String name;
        private boolean allLogin;
        private boolean disable;
        private String description;
        private RoleValue.Form role;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecurity() {
            return appSecurity;
        }

        public void setAppSecurity(String appSecurity) {
            this.appSecurity = appSecurity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAllLogin() {
            return allLogin;
        }

        public void setAllLogin(boolean allLogin) {
            this.allLogin = allLogin;
        }

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public RoleValue.Form getRole() {
            return role;
        }

        public void setRole(RoleValue.Form role) {
            this.role = role;
        }
    }
}
