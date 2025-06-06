package com.test_task.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI electroshopOpenAPI()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Electroshop API")
                        .description("API для управления магазином электроники")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Electroshop Team")
                                .email("support@electroshop.com")));
    }
}