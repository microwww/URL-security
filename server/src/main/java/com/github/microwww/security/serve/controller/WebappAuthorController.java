package com.github.microwww.security.serve.controller;

import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.HttpRequestException;
import com.github.microwww.security.serve.service.WebappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class WebappAuthorController {

    @Autowired
    protected WebappService webappService;
    @Resource
    ValueOperations<String, Serializable> redis;

    public static final ThreadLocal<Webapp> local = new ThreadLocal<>();

    @ModelAttribute
    public void findUserByPrincipal(Principal principal) {
        if (principal != null) {
            Webapp app = getCacheUser(principal.getName()).orElseGet(() -> {
                Optional<Webapp> opt = webappService.getUserByPrincipal(principal);
                if (opt.isPresent()) {
                    refreshCache(opt.get());
                }
                return opt.orElse(null);
            });
            if (app != null) {
                if (!app.isDisable()) {
                    local.set(app);
                    return; // success !
                }
                throw HttpRequestException.newI18N("app.is.disable", app);
            }
        }
        local.remove();
    }

    public Optional<Webapp> getCacheUser(String openid) {
        return Optional.ofNullable((Webapp)redis.get("user:login:" + openid));
    }

    public void refreshCache(Webapp user) {
        redis.set("webapp:login:" + user.getAppId(), user, 1000);
    }

    protected Webapp getLogin() {
        Webapp info = local.get();
        if (info == null) {
            throw new RuntimeException("Not find Login user !");
        }
        return info;
    }

    public <T, U> PageDATA<U> convert(Page<T> page, Function<T, U> fun) {
        List<U> list = page.getContent().stream().map(fun).collect(Collectors.toList());
        return new PageDATA<>(list, page.getPageable(), page.getTotalElements());
    }
}
