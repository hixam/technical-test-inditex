package com.hicham.technicaltestinditex.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for SpringDoc OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Technical Test Inditex - Price Query API")
                        .version("1.0.0")
                        .description("REST API for querying product prices based on application date, product ID, and brand ID")
                        .contact(new Contact()
                                .name("Hicham")
                                .email("hisami27@gmail.com")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local development server"));
    }
}

