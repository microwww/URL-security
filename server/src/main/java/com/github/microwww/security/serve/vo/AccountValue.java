package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.domain.RoleAccount;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AccountValue {

    public static class Simple extends AbstractDomainValue<Account> {

        public Simple(Account domain) {
            super(domain);
        }

        public String getAccount() {
            return super.domain.getAccount();
        }

        public Date getCreateTime() {
            return super.domain.getCreateTime();
        }

        public String getEmail() {
            return super.domain.getEmail();
        }

        public int getId() {
            return super.domain.getId();
        }

        public String getName() {
            return super.domain.getName();
        }

        public String getPhone() {
            return super.domain.getPhone();
        }
    }

    public static class Info extends Simple {

        public Info(Account domain) {
            super(domain);
        }
    }

    public static class More extends Info {

        public More(Account domain) {
            super(domain);
        }

        public List<RoleAccountValue.Simple> getRoleAccounts() {
            List<RoleAccount> list = super.domain.getRoleAccounts();
            return list.stream().map(RoleAccountValue.Simple::new).collect(Collectors.toList());
        }
    }

    public static class Form extends ID {
        private String account;
        private boolean disable = false;
        private String name;
        private String phone;
        private String email;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


}
