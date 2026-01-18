package com.tntgroup.examplepoject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Swagger/OpenAPI configuration.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Example Project API")
                        .description("Demo Spring Boot project for testing TestFlow tool")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TNT Group")
                                .email("tntgroup@example.com")));
    }
}
