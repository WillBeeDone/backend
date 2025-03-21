package de.willbeedone.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "WillBeeDone",
                description = " Web-application designed to connect customers with service providers for household tasks (e.g., minor repairs, gardening, pet care, cleaning, moving, etc.).",
                version = "1.0.0",
                contact = @Contact(
                        name = "Artem",
                        email = "artiomdavidovich@gmail.com",
                        url = "https://willbeedone.github.io/frontend/"
                )
        )
)
public class SwaggerConfig {

}

