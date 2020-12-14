package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Role;
import com.github.microwww.security.serve.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface RoleAuthorityRepository extends JpaRepository<RolePermission, Integer> {

    public Page<RolePermission> findAll(Pageable page);

    public default Optional<RolePermission> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    Page<RolePermission> findByRole(Role role, Pageable of);
}
