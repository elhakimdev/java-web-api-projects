package com.sass.erp.finance.cash.api_service.exceptions.runtime;

import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionCodeConstant;
import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionTypeConstant;

import java.util.List;

public class UnauthorizedRequestException extends ApplicationException {
  public UnauthorizedRequestException(String message, ApplicationExceptionTypeConstant type, int code, List<Object> details) {
    super(message, type, code, details);
  }
}
