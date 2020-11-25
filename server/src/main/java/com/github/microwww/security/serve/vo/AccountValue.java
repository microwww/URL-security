package com.github.microwww.security.serve.vo;

import com.github.microwww.security.serve.domain.*;
import java.util.*;
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
}
