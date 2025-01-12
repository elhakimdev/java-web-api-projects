package com.sass.erp.finance.cash.api_service.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EntityScan(basePackages = "com.sass.erp.finance.cash.api_service.models.entities")  // Replace with your actual entity package
@EnableJpaRepositories(basePackages = "com.sass.erp.finance.cash.api_service.models.repositories")  // Replace with your actual repository package
public class DataSourceConfig {
}
