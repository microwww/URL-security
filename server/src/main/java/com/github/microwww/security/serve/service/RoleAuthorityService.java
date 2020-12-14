package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Role;
import com.github.microwww.security.serve.domain.RolePermission;
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

    public Page<RolePermission> findAll(int page, int size) {
        return this.roleAuthorityRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<RolePermission> findById(int id) {
        return this.roleAuthorityRepository.findById(id);
    }

    public RolePermission getOrElseThrow(int id) {
        return this.roleAuthorityRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(RolePermission.class);
        });
    }

    public Page<RolePermission> findByRole(Role role, int page, int size) {
        return roleAuthorityRepository.findByRole(role, PageRequest.of(page, size));
    }
}
