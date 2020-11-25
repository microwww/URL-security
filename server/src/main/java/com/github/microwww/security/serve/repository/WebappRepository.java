package com.github.microwww.security.serve.repository;

import com.github.microwww.security.serve.domain.Webapp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface WebappRepository extends JpaRepository<Webapp, Integer> {

    public Page<Webapp> findAll(Pageable page);

    public default Optional<Webapp> findById(int id) {
        return this.findById(Integer.valueOf(id));
    }

    Webapp findByAppId(String appId);
}
