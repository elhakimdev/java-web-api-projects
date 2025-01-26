package com.sass.erp.finance.cash.api_service.http.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestfullApiResponseError {
  protected String errorMessage;
  protected String errorType;
  protected String errorCode;
  protected String errorTraceId;
  protected Map<String, Object> errorData;
}
