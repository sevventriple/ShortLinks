package com.example.LinkShortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Shortener link API")
                                .version("1.0.0")
                                .contact(new Contact()
                                        .email("alexkost631@gmail.com")
                                        .name("Алексей Кострыгин")
                                )
                );
    }
}
