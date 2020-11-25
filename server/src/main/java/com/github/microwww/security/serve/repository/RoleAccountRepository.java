package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Account;
import com.github.microwww.security.serve.domain.Role;
import com.github.microwww.security.serve.domain.RoleAccount;
import com.github.microwww.security.serve.domain.Webapp;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface RoleAccountRepository extends JpaRepository<RoleAccount, Integer> {

    public Page<RoleAccount> findAll(Pageable page);

    public default Optional<RoleAccount> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    RoleAccount findByAccount(Account acc);

    Page<RoleAccount> findByRoleWebappAndAccount(Webapp webapp, Account acc, Pageable of);
}
