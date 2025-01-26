package com.sass.erp.finance.cash.api_service.http.middleware;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class AuthorizeHttpRequestMiddleware {
  public static void enableHttpMiddleware(
    AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry
  ){
    registry.requestMatchers("/api/users/**").permitAll();
    registry.anyRequest().hasRole("ADMIN");
  }
}

