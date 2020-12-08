package com.github.microwww.security.serve.controller.admin;

import com.github.microwww.security.serve.domain.Webapp;
import com.github.microwww.security.serve.exception.HttpRequestException;
import com.github.microwww.security.serve.service.PersistFilter;
import com.github.microwww.security.serve.service.WebappService;
import com.github.microwww.security.serve.vo.WebappValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WebappPersistFilter implements PersistFilter<WebappValue.Form, Webapp> {

    @Autowired
    WebappService webappService;

    @Override
    public void beforeSave(WebappValue.Form form, Webapp entity) {
        Optional<Webapp> web = webappService.findByAppId(form.getAppId());
        if (web.isPresent()) {
            if (web.get().getId() != form.getId()) {
                throw HttpRequestException.newI18N("webapp.app.id.exist", web.get());
            }
        }
    }

    @Override
    public void afterSave(WebappValue.Form form, Webapp entity) {

    }
}
