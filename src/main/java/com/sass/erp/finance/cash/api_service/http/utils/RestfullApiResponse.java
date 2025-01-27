package com.sass.erp.finance.cash.api_service.http.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestfullApiResponse<T, E> {
  protected RestfullApiResponseStatus status;
  protected String message;
  protected Integer statusCode;
  protected String statusText;
  protected LocalDateTime timestamp;
  protected T data;
  protected RestfullApiResponseError<E> error;
}
