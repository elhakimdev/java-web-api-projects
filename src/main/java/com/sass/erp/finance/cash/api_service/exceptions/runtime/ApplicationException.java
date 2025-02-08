package com.sass.erp.finance.cash.api_service.exceptions.runtime;

import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionConstant;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseError;

import java.util.List;

public class ApplicationException extends RuntimeException {

  protected ApplicationExceptionConstant type;

  protected int code;

  protected List<Object> details;

  ApplicationException(
    String message,
    ApplicationExceptionConstant type,
    int code,
    List<Object> details
  ){
    super(message);
    this.code = code;
    this.type = type;
    this.details = details;
  }

  public <Ex extends ApplicationException> RestfullApiResponseError<Ex> toResponse(String traceId) {
    return new RestfullApiResponseError<>(
      this.type.toString(),
      String.format("0x%04X", this.code),
      this.details
    );
  }
}
