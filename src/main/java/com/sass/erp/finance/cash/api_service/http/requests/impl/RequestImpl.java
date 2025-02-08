package com.sass.erp.finance.cash.api_service.http.requests.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sass.erp.finance.cash.api_service.http.requests.Request;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;

@Data
@NoArgsConstructor
public abstract class RequestImpl implements Request {


  private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private static final Validator validator = factory.getValidator();

  protected abstract boolean authorize();

  @Override
  public void validate() throws ConstraintViolationException {

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
