package br.com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"br.com.project.model"})
@EnableJpaRepositories(basePackages = {"br.com.project.repositories"})
@ComponentScan(basePackages = {"br.com.project.services",
		"br.com.project.controllers",
		"br.com.project.exceptions",
		"br.com.project.exceptions.handler"})
@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

}
