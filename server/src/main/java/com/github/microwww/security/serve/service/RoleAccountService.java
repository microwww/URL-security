package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.domain.RoleAccount;
import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.RoleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service()
@Transactional()
public class RoleAccountService {

    @Autowired()
    private RoleAccountRepository roleAccountRepository;

    public RoleAccountRepository getRoleAccountRepository() {
        return roleAccountRepository;
    }

    public void setRoleAccountRepository(RoleAccountRepository roleAccountRepository) {
        this.roleAccountRepository = roleAccountRepository;
    }

    public Page<RoleAccount> findAll(int page, int size) {
        return this.roleAccountRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<RoleAccount> findById(int id) {
        return this.roleAccountRepository.findById(id);
    }

    public RoleAccount getOrElseThrow(int id) {
        return this.roleAccountRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(RoleAccount.class);
        });
    }

    public Page<RoleAccount> findByAccount(Webapp webapp, Account acc, int page, int size) {
        return roleAccountRepository.findByRoleWebappAndAccount(webapp, acc, PageRequest.of(page, size));
    }
}
