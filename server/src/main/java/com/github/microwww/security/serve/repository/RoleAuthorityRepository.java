package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Role;
import com.github.microwww.security.serve.domain.RoleAuthority;
import com.github.microwww.security.serve.domain.Webapp;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Integer> {

    public Page<RoleAuthority> findAll(Pageable page);

    public default Optional<RoleAuthority> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    Page<RoleAuthority> findByRole(Role role, Pageable of);
}
