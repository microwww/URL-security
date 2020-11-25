package com.github.microwww.security.serve.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.schema.ApiModelTypeNameProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Component
    public class Knife implements WebMvcConfigurer {
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    public Docket createRestApi() {
        Parameter token = new ParameterBuilder().name("token").description("user ticket")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

        return new Docket(DocumentationType.SWAGGER_2)
                //.enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.microwww.security.serve.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(Collections.singletonList(token));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("项目api文档")
                .contact(new Contact("xxxx 科技有限公司", "http://www.example.com/index.html", "mail@mail.example.com"))
                .version("0.0.1")
                .build();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Component
    public class ModelTypeNameProvider extends ApiModelTypeNameProvider {
        @Override
        public String nameFor(Class<?> type) {
            String name = type.getName();
            int i = name.lastIndexOf('.');
            if (i > 0) {
                return name.substring(i + 1);
            }
            return super.nameFor(type);
        }
    }
}