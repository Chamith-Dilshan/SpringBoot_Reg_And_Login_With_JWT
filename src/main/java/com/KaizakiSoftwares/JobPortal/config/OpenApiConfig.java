package com.KaizakiSoftwares.JobPortal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Chamith Dilshan",
                        email = "Chamithzdilshann@gmail.com",
                        url = " "
                ),
                description = "Open API Documentation for Job Portal Web Application",
                title = "OpenAPI specification - Chamith :)",
                version = "0.9",
                license = @License(
                        name = "Licence Name",
                        url = " "
                ),
                termsOfService = "Terms of Services"
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "AWS Env",
                        url = " "
                )
        },
        //if you add security here, it will apply to all the
        //controllers
        security ={
                @SecurityRequirement(
                        name = "BearerAuth"
                )
        }
)
//@SecurityRequirments(name = "BearerAuth") need to add this annotation in the Controller class, if you
//want to define security scheme differently for your controllers
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
