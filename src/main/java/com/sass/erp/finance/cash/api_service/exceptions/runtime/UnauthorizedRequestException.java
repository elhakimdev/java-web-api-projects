package com.sass.erp.finance.cash.api_service.exceptions.runtime;

import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionConstant;

import java.util.List;

public class UnauthorizedRequestException extends ApplicationException {
  UnauthorizedRequestException(String message, ApplicationExceptionConstant type, int code, List<Object> details) {
    super(message, type, code, details);
  }
}
