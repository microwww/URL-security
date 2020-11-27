package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.RoleAccount;

import java.util.Date;

public abstract class RoleAccountValue {

    public static class Simple extends AbstractDomainValue<RoleAccount> {

        public Simple(RoleAccount domain) {
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

        public Info(RoleAccount domain) {
            super(domain);
        }

        public AccountValue.Simple getAccount() {
            return new AccountValue.Simple(super.domain.getAccount());
        }

        public RoleValue.Simple getRole() {
            return new RoleValue.Simple(super.domain.getRole());
        }
    }

    public static class More extends Info {

        public More(RoleAccount domain) {
            super(domain);
        }
    }

    public static class Form extends ID {
        private AccountValue.Form account;
        private RoleValue.Form role;

        public AccountValue.Form getAccount() {
            return account;
        }

        public void setAccount(AccountValue.Form account) {
            this.account = account;
        }

        public RoleValue.Form getRole() {
            return role;
        }

        public void setRole(RoleValue.Form role) {
            this.role = role;
        }
    }
}
