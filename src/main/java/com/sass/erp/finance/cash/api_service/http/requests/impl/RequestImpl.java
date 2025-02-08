package com.sass.erp.finance.cash.api_service.http.requests.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionCodeConstant;
import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionTypeConstant;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.UnauthorizedRequestException;
import com.sass.erp.finance.cash.api_service.http.requests.Request;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;

@Data
@NoArgsConstructor
public abstract class RequestImpl implements Request {

  private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

  private static final Validator validator = factory.getValidator();

  protected abstract boolean authorize();

  @Override
  public void validate() throws ConstraintViolationException {

    if (!authorize()) {
      throw new UnauthorizedRequestException("Unauthorized to perform this action due the restriction policies",
        ApplicationExceptionTypeConstant.GENERAL_ERROR,
        ApplicationExceptionCodeConstant.OPERATION_NOT_ALLOWED,
        List.of("Require fulfill personal identify", "kyc required", "should be logged in first", "missing scope")
      );
    }

    Set<ConstraintViolation<RequestImpl>> violations = validator.validate(this);

    if (!violations.isEmpty()) {
      Map<String, String> errors = new HashMap<>();
      for (ConstraintViolation<RequestImpl> violation : violations) {
        errors.put(violation.getPropertyPath().toString(), violation.getMessage());
      }
      throw new ConstraintViolationException("Validation failed", null, errors.toString());
    }
  }

  @Override
  public <Req extends Request> Req validated() {
    return null;
  }

  @Override
  public String toString() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(this); // Converts object to JSON
    } catch (Exception e) {
      return super.toString(); // Fallback
    }
  }
}
