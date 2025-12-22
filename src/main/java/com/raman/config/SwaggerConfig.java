package com.raman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI bankingServiceOpenAPI() {
	    return new OpenAPI()
	        .info(new Info()
	            .title("Banking Management System API")
	            .description("API documentation for the Banking Management System")
	            .version("1.0.0")
	            .contact(new io.swagger.v3.oas.models.info.Contact()
	                .name("Raman Support")
	                .email("support@raman-banking.com"))
	            .license(new io.swagger.v3.oas.models.info.License()
	                .name("Apache 2.0")
	                .url("http://springdoc.org")));
	}
}
