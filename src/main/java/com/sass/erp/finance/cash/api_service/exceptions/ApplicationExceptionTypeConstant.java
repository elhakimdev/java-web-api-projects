package com.sass.erp.finance.cash.api_service.exceptions;

public enum ApplicationExceptionTypeConstant {
  VALIDATION_ERROR("Validation Error"),
  AUTHORIZATION_ERROR("Authorization Error"),
  AUTHENTICATION_ERROR("Authentication Error"),
  TRANSACTION_ERROR("Transaction Error"),
  GENERAL_ERROR("General Error");
  ApplicationExceptionTypeConstant(String type) {
  }
}
