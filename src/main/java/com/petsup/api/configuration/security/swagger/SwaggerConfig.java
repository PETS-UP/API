package com.petsup.api.configuration.security.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PetsUp API",
                description = "Serviços de agendamento e geolocalização de pet shops",
                contact = @Contact(
                        name = "PetsUp",
                        url = "https://github.com/PETS-UP",
                        email = "231-3adsc-grupo3@bandtec.com.br"
                ),
                license = @License(name = "UNLICENSED"),
                version = "1.5.2"
        )
)
@SecurityScheme(
        name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"
)
public class SwaggerConfig {

}
