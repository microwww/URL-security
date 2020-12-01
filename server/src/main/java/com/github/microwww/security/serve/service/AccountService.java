package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.domain.AuthAccount;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.AccountRepository;
import com.github.microwww.security.serve.repository.AuthAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service()
@Transactional()
public class AccountService {

    @Autowired()
    private AccountRepository accountRepository;
    @Autowired()
    private AuthAccountRepository authAccountRepository;

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Page<Account> findAll(int page, int size) {
        return this.accountRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Account> findById(int id) {
        return this.accountRepository.findById(id);
    }

    public Account getOrElseThrow(int id) {
        return this.accountRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(Account.class);
        });
    }

    public Optional<Account> getUserByPrincipal(Principal principal) {
        Account acc = accountRepository.findByAccount(principal.getName());
        return Optional.ofNullable(acc);
    }

    public Optional<Account> findByAccount(String account) {
        return Optional.ofNullable(this.accountRepository.findByAccount(account));
    }

    public Optional<AuthAccount> findAuthAccount(Account account) {
        return Optional.ofNullable(authAccountRepository.findByAccountAndType(account, AuthAccount.Type.PASSWORD));
    }
}
