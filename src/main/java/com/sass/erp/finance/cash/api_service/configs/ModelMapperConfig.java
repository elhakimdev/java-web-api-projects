package com.sass.erp.finance.cash.api_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper mapper() {
    return new ModelMapper();
  }

  @Bean
  public ObjectMapper objectMapper () {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());  // Register the JavaTimeModule for Java 8 date/time support
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Disable writing date as timestamp (array of integer time format)
    return objectMapper;
  }
}
