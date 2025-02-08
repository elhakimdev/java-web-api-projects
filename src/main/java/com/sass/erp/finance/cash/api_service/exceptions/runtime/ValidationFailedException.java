package com.sass.erp.finance.cash.api_service.exceptions.runtime;

import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionTypeConstant;

public class ValidationFailedException extends ApplicationException {
  public ValidationFailedException(String message, ApplicationExceptionTypeConstant type, int code, Object details) {
    super(message, type, code, details);
  }
}
