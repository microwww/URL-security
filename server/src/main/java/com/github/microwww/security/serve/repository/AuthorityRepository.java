package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Permission;
import com.github.microwww.security.serve.domain.Webapp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Permission, Integer> {

    public Page<Permission> findAll(Pageable page);

    public default Optional<Permission> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    Page<Permission> findByWebapp(Webapp webapp, Pageable of);

    Permission findByWebappAndUri(Webapp webapp, String uri);
}
