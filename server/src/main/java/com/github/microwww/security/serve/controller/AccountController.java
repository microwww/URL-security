package com.github.microwww.security.serve.controller;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.vo.AccountValue;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public class AccountController extends AccountAuthorController {

    @ApiOperation("用户信息")
    @GetMapping("/me/info")
    public AccountValue.Simple loginAccount() {
        Account login = this.getLogin();
        Account acc = accountService.findById(login.getId()).orElseThrow(() -> new ExistException.NotExist(Account.class));
        return new AccountValue.Simple(acc);
    }
}
