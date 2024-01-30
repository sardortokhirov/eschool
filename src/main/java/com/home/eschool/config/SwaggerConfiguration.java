package com.home.eschool.config;

import com.sun.tools.javac.util.List;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Pulse Api written by Samandar")
                        .version("1.0.1")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache Say Hello World!").url("https://apache.co")))
                .servers(List.of(
                        new Server().url("http://134.122.65.102/").description("Just A Simple Server")
                ));
    }
}

