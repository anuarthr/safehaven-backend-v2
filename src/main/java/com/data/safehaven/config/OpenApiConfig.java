package com.data.safehaven.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger. Expone la documentación interactiva en
 * {@code /swagger-ui.html} y declara el esquema de seguridad JWT (Bearer) para
 * poder autenticar las llamadas desde la propia UI.
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI safeHavenOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SafeHaven API")
                        .description("API REST para la gestión de una clínica de psicología: "
                                + "autenticación JWT, pacientes, psicólogos, administradores, "
                                + "citas, consultorios y roles.")
                        .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
