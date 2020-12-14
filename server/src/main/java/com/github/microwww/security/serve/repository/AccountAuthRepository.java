package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.domain.AccountAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountAuthRepository extends JpaRepository<AccountAuth, Integer> {

    public Page<AccountAuth> findAll(Pageable page);

    public default Optional<AccountAuth> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    AccountAuth findByAccountAccountAndType(String account, AccountAuth.Type type);

    List<AccountAuth> findByAccount(Account acc);

    AccountAuth findByAccountAndType(Account acc, AccountAuth.Type type);
}
