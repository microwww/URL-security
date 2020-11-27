package com.github.microwww.security.serve.controller.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.microwww.security.serve.controller.AccountAuthorController;
import com.github.microwww.security.serve.controller.PageDATA;
import com.github.microwww.security.serve.domain.AbstractBasicEntity;
import com.github.microwww.security.serve.service.EntityService;
import com.github.microwww.security.serve.vo.EntityEnum;
import com.github.microwww.security.serve.vo.ID;
import com.github.microwww.security.serve.vo.Pagers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"超级管理员", "实体管理"})
@RestController
@RequestMapping("/api/admin")
public class EntityManagerController extends AccountAuthorController {

    private static final String prefix = ID.class.getPackage().getName();
    private static final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);

    @Autowired
    EntityService entityService;

    @ApiOperation("实体/查询数据列表")
    @PostMapping(value = "/entity.list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageDATA<?> listEntity(@RequestBody Pagers data) {
        EntityEnum entity = data.getEntity();
        Page<?> info = entityService.listEntity(data, entity);
        return convert(info, entity::map);
    }

    @ApiOperation("保存实体")
    @PostMapping(value = "/save/{entity}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object saveEntity(@PathVariable("entity") EntityEnum entity, @RequestBody String data) throws ClassNotFoundException, JsonProcessingException {
        Class<ID> clazz = (Class<ID>) Class.forName(prefix + "." + entity.name() + "Value$Form");
        ID form = mapper.readValue(data, clazz);
        Class<AbstractBasicEntity> en = entity.getEntity();

        AbstractBasicEntity exec = entityService.saveEntity(form, en);
        return entity.map(exec);
    }

}
