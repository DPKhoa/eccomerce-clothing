package com.app.projectstyleecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectStyleEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectStyleEcommerceApplication.class, args);
	}

}
