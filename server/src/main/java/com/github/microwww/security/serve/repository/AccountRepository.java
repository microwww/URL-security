package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Page<Account> findAll(Pageable page);

    public default Optional<Account> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    Account findByAccount(String account);
}
