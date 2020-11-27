package com.github.microwww.security.serve.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.microwww.security.serve.domain.AbstractBasicEntity;
import com.github.microwww.security.serve.service.PersistFilter;
import com.github.microwww.security.serve.vo.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerEntitySave implements PersistFilter<ID, AbstractBasicEntity> {
    private static final Logger logger = LoggerFactory.getLogger(LoggerEntitySave.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isSupport(Class form, Class entity) {
        return true;
    }

    @Override
    public void beforeSave(ID form, AbstractBasicEntity entity) {
        try {
            logger.info("Save entity : [{}], ID:[{}], form data: [{}]", entity.getClass().getName(), entity.getId(), mapper.writeValueAsString(form));
        } catch (JsonProcessingException e) {
            logger.error("json error : {}", e.getMessage());
        }
    }

    @Override
    public void afterSave(ID form, AbstractBasicEntity entity) {
    }
}
