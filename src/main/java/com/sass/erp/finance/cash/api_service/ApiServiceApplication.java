package com.sass.erp.finance.cash.api_service;

import com.sass.erp.finance.cash.api_service.database.factories.UserFactory;
import com.sass.erp.finance.cash.api_service.database.seeders.UserSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiServiceApplication {

	@Autowired
	private UserSeeder userSeeder;

	public static void main(String[] args) {
		SpringApplication.run(ApiServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			userSeeder.run();
		};
	}
}
