package com.sass.erp.finance.cash.api_service.http.requests;

import com.sass.erp.finance.cash.api_service.exceptions.runtime.ValidationFailedException;
import com.sass.erp.finance.cash.api_service.http.requests.impl.RequestImpl;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface Request {
  Set<ConstraintViolation<RequestImpl>> validate() throws ValidationFailedException;
  <Req extends Request> Req validated();
}
