package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Permission;
import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service()
@Transactional()
public class PermissionService {

    @Autowired()
    private PermissionRepository permissionRepository;

    public Page<Permission> findAll(int page, int size) {
        return this.permissionRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Permission> findById(int id) {
        return this.permissionRepository.findById(id);
    }

    public Permission getOrElseThrow(int id) {
        return this.permissionRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(Permission.class);
        });
    }

    public Page<Permission> findByWebapp(Webapp webapp, int page, int size) {
        return permissionRepository.findByWebapp(webapp, PageRequest.of(page, size));
    }

    public List<Permission> save(Webapp webapp, String... url) {
        List<Permission> list = new ArrayList<>();
        for (String u : url) {
            Permission au = permissionRepository.findByWebappAndUri(webapp, u);
            if (au == null) {
                au = new Permission();
                au.setWebapp(webapp);
                au.setUri(u);
                permissionRepository.save(au);
            }
            list.add(au);
        }
        return list;
    }
}
