package com.optus.counterapi.documentation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration file.
 * URL to swagger page: http://localhost:8080/swagger-ui.html
 * 
 * @author kalyan
 *
 */
@SpringBootApplication
@EnableSwagger2
public class SpringSwaggerConfig {                                    
    @Bean
    public Docket counterApi() {        
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.optus.counterapi.controller")).build();
    }
}
