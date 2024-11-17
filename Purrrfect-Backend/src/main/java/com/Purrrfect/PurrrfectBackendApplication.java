package com.Purrrfect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Purrrfect.Repo")
public class PurrrfectBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(PurrrfectBackendApplication.class, args);
	}
}
