package com.api.api_springboot;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Swagger OpenApi", version = "1", description = "API de cliente desenvolvida para teste técnico backend"))
@SpringBootApplication
public class ApiSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSpringbootApplication.class, args);
	}

}
