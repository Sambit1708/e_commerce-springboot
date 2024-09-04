package com.eCommerce.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name="Sambit Khandai",
						email="sambitk2001@gmail.com",
						url="http://localhost:8080/"
				),
				description = "This is a e-commerce application",
				title = "ECommerce App",
				version = "1.0",
				license = @License(
						name = "License Name",
						url = "https://some-url.com"
				),
				termsOfService = "Tems are simple"
		),
		servers = {
				@Server(
						description = "DEV ENV",
						url = "http://localhost:8080"
				),
				@Server(
						description = "PROD ENV",
						url = "http://localhost:8080"
				)
		}
)
//@SecurityScheme(
//		name = "BearerAuth",
//		description = "JWT auth description",
//		scheme = "Bearer",
//		type = SecuritySchemeType.HTTP,
//		bearerFormat = "JWT",
//		in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {

}
