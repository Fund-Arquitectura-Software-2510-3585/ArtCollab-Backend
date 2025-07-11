package com.drawnet.artcolab.libroservice.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI ArtCollabOpenApi() {
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title("CollaborativeProjectsService API")
                        .description("ArtCollab REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("ArtCollab Wiki Documentation")
                        .url(""));

        return openApi;
    }
}

