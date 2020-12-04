package com.github.microwww.security.serve.controller;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.exception.HttpRequestException;
import com.github.microwww.security.serve.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AccountAuthorController {

    @Autowired
    protected AccountService accountService;
    @Resource
    ValueOperations<String, Serializable> redis;

    public static final ThreadLocal<Account> local = new ThreadLocal<>();

    @ModelAttribute
    public void findUserByPrincipal(Principal principal) {
        if (principal != null) {
            Account acc = getCacheUser(principal.getName()).orElseGet(() -> {
                Optional<Account> opt = accountService.getUserByPrincipal(principal);
                if (opt.isPresent()) {
                    refreshCache(opt.get());
                }
                return opt.orElse(null);
            });
            if (acc != null) {
                if (!acc.isDisable()) {
                    local.set(acc);
                    return; // success !
                }
                throw HttpRequestException.newI18N("account.is.disable", acc);
            }
        }
        local.remove();
    }

    public Optional<Account> getCacheUser(String account) {
        return Optional.ofNullable((Account) redis.get("account:login:" + account));
    }

    public void refreshCache(Account user) {
        redis.set("account:login:" + user.getAccount(), user, 1, TimeUnit.HOURS);
    }

    protected Account getLogin() {
        Account info = local.get();
        if (info == null) {
            throw new RuntimeException("Not find Login user !");
        }
        return info;
    }

    public <T, U> PageDATA<U> convert(Page<T> page, Function<T, U> fun) {
        List<U> list = page.getContent().stream().map(fun).collect(Collectors.toList());
        return new PageDATA<>(list, page.getPageable(), page.getTotalElements());
    }
}
