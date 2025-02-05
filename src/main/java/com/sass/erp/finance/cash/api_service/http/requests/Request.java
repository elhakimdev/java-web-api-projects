package com.sass.erp.finance.cash.api_service.http.requests;

import org.hibernate.exception.ConstraintViolationException;

public interface Request {
  void validate() throws ConstraintViolationException;
  <Req extends Request> Req validated();
}
