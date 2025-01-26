package com.sass.erp.finance.cash.api_service.configs;

import com.sass.erp.finance.cash.api_service.http.middleware.AuthorizeHttpRequestMiddleware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  @Order(1)
  public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
    http
      .securityMatcher("/api/**")
      .authorizeHttpRequests(AuthorizeHttpRequestMiddleware::enableHttpMiddleware)
      .httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
