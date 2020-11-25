package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Authority;
import com.github.microwww.security.serve.domain.Webapp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    public Page<Authority> findAll(Pageable page);

    public default Optional<Authority> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    Page<Authority> findByWebapp(Webapp webapp, Pageable of);

    Authority findByWebappAndUri(Webapp webapp, String uri);
}
