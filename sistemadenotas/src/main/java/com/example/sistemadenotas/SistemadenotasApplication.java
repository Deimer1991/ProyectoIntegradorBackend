package com.example.sistemadenotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SistemadenotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemadenotasApplication.class, args);
	}

}
