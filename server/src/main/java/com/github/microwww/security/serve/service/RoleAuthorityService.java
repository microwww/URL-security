package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Role;
import com.github.microwww.security.serve.domain.RoleAccount;
import com.github.microwww.security.serve.domain.RoleAuthority;
import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.RoleAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Service()
@Transactional()
public class RoleAuthorityService {

    @Autowired()
    private RoleAuthorityRepository roleAuthorityRepository;

    public RoleAuthorityRepository getRoleAuthorityRepository() {
        return roleAuthorityRepository;
    }

    public void setRoleAuthorityRepository(RoleAuthorityRepository roleAuthorityRepository) {
        this.roleAuthorityRepository = roleAuthorityRepository;
    }

    public Page<RoleAuthority> findAll(int page, int size) {
        return this.roleAuthorityRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<RoleAuthority> findById(int id) {
        return this.roleAuthorityRepository.findById(id);
    }

    public RoleAuthority getOrElseThrow(int id) {
        return this.roleAuthorityRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(RoleAuthority.class);
        });
    }

    public Page<RoleAuthority> findByRole(Role role, int page, int size) {
        return roleAuthorityRepository.findByRole(role, PageRequest.of(page, size));
    }
}
