package com.residencia.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger - Midas backend", version = "1", description = "Api desenvolvida para o web sistema de gestão financeira Midas"))
@EntityScan("com.residencia.backend.modules.models")
@EnableJpaRepositories("com.residencia.backend.modules.repositories")
public class MidasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidasBackendApplication.class, args);
	}

}
