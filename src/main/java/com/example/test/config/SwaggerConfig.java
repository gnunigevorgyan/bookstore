package com.example.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Configuration class for enabling the swagger ui.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @return instance of Docket class's.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.test.controller"))
                .paths(PathSelectors.ant("/api/**")).build().pathMapping("/")
                .apiInfo(apiInfo());
    }

    /**
     * @return instance of ApiInfo.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Test API")
                .version("API 1.0.0")
                .build();
    }
}