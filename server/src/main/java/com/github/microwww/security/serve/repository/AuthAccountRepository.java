package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.domain.AuthAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthAccountRepository extends JpaRepository<AuthAccount, Integer> {

    public Page<AuthAccount> findAll(Pageable page);

    public default Optional<AuthAccount> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    AuthAccount findByAccountAccountAndType(String account, AuthAccount.Type type);

    List<AuthAccount> findByAccount(Account acc);

    AuthAccount findByAccountAndType(Account acc, AuthAccount.Type type);
}
