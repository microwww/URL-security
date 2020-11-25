package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Role;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

@Service()
@Transactional()
public class RoleService {

    @Autowired()
    private RoleRepository roleRepository;

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Page<Role> findAll(int page, int size) {
        return this.roleRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Role> findById(int id) {
        return this.roleRepository.findById(id);
    }

    public Role getOrElseThrow(int id) {
        return this.roleRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(Role.class);
        });
    }
}
