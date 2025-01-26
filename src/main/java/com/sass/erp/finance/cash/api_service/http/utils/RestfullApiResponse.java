package com.sass.erp.finance.cash.api_service.http.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestfullApiResponse<T> {
  protected RestfullApiResponseStatus status;
  protected String message;
  protected Integer statusCode;
  protected String statusText;
  protected Optional<RestfullApiResponseError> error;
  protected LocalDateTime timestamp;
  protected Optional<T> data;

  public static RestfullApiResponse<?> toResponse(){
    return new RestfullApiResponse<>();
  }
}
