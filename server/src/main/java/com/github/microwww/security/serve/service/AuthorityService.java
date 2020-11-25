package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Authority;
import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.AuthorityRepository;
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
public class AuthorityService {

    @Autowired()
    private AuthorityRepository authorityRepository;

    public AuthorityRepository getAuthorityRepository() {
        return authorityRepository;
    }

    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Page<Authority> findAll(int page, int size) {
        return this.authorityRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Authority> findById(int id) {
        return this.authorityRepository.findById(id);
    }

    public Authority getOrElseThrow(int id) {
        return this.authorityRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(Authority.class);
        });
    }

    public Page<Authority> findByWebapp(Webapp webapp, int page, int size) {
        return authorityRepository.findByWebapp(webapp, PageRequest.of(page, size));
    }

    public List<Authority> save(Webapp webapp, String... url) {
        List<Authority> list = new ArrayList<>();
        for (String u : url) {
            Authority au = authorityRepository.findByWebappAndUri(webapp, u);
            if (au == null) {
                au = new Authority();
                au.setWebapp(webapp);
                au.setUri(u);
                authorityRepository.save(au);
            }
            list.add(au);
        }
        return list;
    }
}
