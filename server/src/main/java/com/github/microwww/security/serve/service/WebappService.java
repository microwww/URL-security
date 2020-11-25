package com.github.microwww.security.serve.service;

import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.ExistException;
import com.github.microwww.security.serve.repository.WebappRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;
import java.util.Optional;

@Service()
@Transactional()
public class WebappService {

    @Autowired()
    private WebappRepository webappRepository;

    public WebappRepository getWebappRepository() {
        return webappRepository;
    }

    public void setWebappRepository(WebappRepository webappRepository) {
        this.webappRepository = webappRepository;
    }

    public Page<Webapp> findAll(int page, int size) {
        return this.webappRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Webapp> findById(int id) {
        return this.webappRepository.findById(id);
    }

    public Webapp getOrElseThrow(int id) {
        return this.webappRepository.findById(id).orElseThrow(() -> {
            return new ExistException.NotExist(Webapp.class);
        });
    }

    public Optional<Webapp> findByAppId(String appId) {
        return Optional.ofNullable(webappRepository.findByAppId(appId));
    }

    public Optional<Webapp> getUserByPrincipal(Principal principal) {
        String appId = principal.getName();
        return findByAppId(appId);
    }
}
