package com.github.microwww.security.serve.controller.admin;

import com.github.microwww.security.serve.controller.AccountController;
import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.service.PersistFilter;
import com.github.microwww.security.serve.vo.AccountValue;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountPersistFilter implements PersistFilter<AccountValue.Form, Account> {
    @Autowired
    AccountController accountController;

    @Override
    public void beforeSave(AccountValue.Form form, Account entity) {
    }

    @Override
    public void afterSave(AccountValue.Form form, Account entity) {
        accountController.refreshCache(entity);
    }
}
