package com.api.api_springboot.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Clientes Desenvolvida para Teste Backend")
                        .version("v1")
                        .description("SPRINGBOOT REST API")
                        );
    }

}
