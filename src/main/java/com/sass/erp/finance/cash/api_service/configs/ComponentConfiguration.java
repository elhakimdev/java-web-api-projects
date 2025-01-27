package com.sass.erp.finance.cash.api_service.configs;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
  "com.sass.erp.finance.cash.api_service.http.resources",
})
public class ComponentConfiguration {
}
