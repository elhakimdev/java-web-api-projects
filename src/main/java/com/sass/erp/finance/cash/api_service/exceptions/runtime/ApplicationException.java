package com.sass.erp.finance.cash.api_service.exceptions.runtime;

import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionCodeConstant;
import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionTypeConstant;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseError;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {

  protected ApplicationExceptionTypeConstant type;

  protected int code;

  protected List<Object> details;

  ApplicationException(
    String message,
    ApplicationExceptionTypeConstant type,
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
      traceId,
      this.details
    );
  }
}
