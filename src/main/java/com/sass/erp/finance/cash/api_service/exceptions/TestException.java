package com.sass.erp.finance.cash.api_service.exceptions;

public class TestException  extends BaseException {

  public TestException(Integer errorCode, String errorType) {
    super(errorCode, errorType);
  }
}
