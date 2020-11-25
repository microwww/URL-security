package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Page<Role> findAll(Pageable page);

    public default Optional<Role> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }
}
