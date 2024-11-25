package com.example.gestionIndemnites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.gestionIndeminites")
@EnableJpaRepositories(basePackages = "com.example.gestionIndeminites.repository")
@EntityScan(basePackages = "com.example.gestionIndeminites.entity")

public class GestionIndeminites1Application {

	public static void main(String[] args) {
		SpringApplication.run(GestionIndeminites1Application.class, args);
	}

}
