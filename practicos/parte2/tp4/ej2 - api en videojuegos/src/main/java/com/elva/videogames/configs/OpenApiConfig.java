package com.elva.videogames.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Videogames API",
                version = "1.0.0",
                description = "REST API to manage videogames, genres, and studios",
                contact = @Contact(name = "Elva", email = "support@example.com"),
                license = @License(name = "MIT")
        )
)
@Configuration
public class OpenApiConfig {
}

