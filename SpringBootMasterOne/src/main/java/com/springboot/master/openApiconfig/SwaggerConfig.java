package com.springboot.master.openApiconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class SwaggerConfig {

	@Autowired
	private Environment env;

	@Bean
	
	public OpenAPI customOpenAPI() {
		String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "default";

		return new OpenAPI()
				.info(new Info().title(getTitleForProfile(profile))
						.description("API documentation for " + profile + " environment")
						.contact(new Contact().name("Amarjeet Kumar Singh").email("amarjeetcs79@gmail.com"))
						.license(
								new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
						.version("1.0.0"))
				.servers(List.of(new Server().url(getServerUrlForProfile("default")).description("Development Server"),
						new Server().url(getServerUrlForProfile("qa")).description("QA Server"),
						new Server().url(getServerUrlForProfile("uat")).description("UAT Server"),
						new Server().url(getServerUrlForProfile("pilot")).description("Pilot Server"),
						new Server().url(getServerUrlForProfile("prod")).description("Production Server")));
	}

	private String getTitleForProfile(String profile) {
		switch (profile) {
		case "prod":
			return "Production API Documentation";
		case "uat":
			return "UAT API Documentation";
		case "qa":
			return "QA API Documentation";
		case "pilot":
			return "Pilot API Documentation";
		default:
			return "Development API Documentation";
		}
	}

	private String getServerUrlForProfile(String profile) {
		switch (profile) {
		case "prod":
			return "http://localhost:8083";
		case "uat":
			return "http://localhost:8082";
		case "qa":
			return "http://localhost:8081";
		case "pilot":
			return "http://localhost:8080";
		default:
			return "http://localhost:9090";
		}
	}
}
