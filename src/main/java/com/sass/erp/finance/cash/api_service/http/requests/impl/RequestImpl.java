package com.sass.erp.finance.cash.api_service.http.requests.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionCodeConstant;
import com.sass.erp.finance.cash.api_service.exceptions.ApplicationExceptionTypeConstant;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.UnauthorizedRequestException;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.ValidationFailedException;
import com.sass.erp.finance.cash.api_service.http.requests.Request;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@NoArgsConstructor
public abstract class RequestImpl implements Request {

  private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

  private static final Validator validator = factory.getValidator();

  protected abstract boolean authorize();

  @Override
  public Set<ConstraintViolation<RequestImpl>> validate() throws ValidationFailedException {

    if (!authorize()) {
      throw new UnauthorizedRequestException(
        "Unauthorized to perform this action due the restriction policies",
        ApplicationExceptionTypeConstant.GENERAL_ERROR,
        ApplicationExceptionCodeConstant.OPERATION_NOT_ALLOWED,
        List.of("Require fulfill personal identify", "kyc required", "should be logged in first", "missing scope")
      );
    }

    return validator.validate(this);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <Req extends Request> Req validated() throws ValidationFailedException {
    Set<ConstraintViolation<RequestImpl>> violationConstrains = this.validate();

    if(!violationConstrains.isEmpty()) {
      Map<String, List<String>> errors = violationConstrains.stream().collect(
        Collectors.groupingBy(v -> v.getPropertyPath().toString(), Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList()))
      );
      throw  new ValidationFailedException(
        "Validation failed",
        ApplicationExceptionTypeConstant.VALIDATION_ERROR,
        ApplicationExceptionCodeConstant.INVALID_REQUEST,
        errors
      );
    }

    return (Req) filterValidFields(violationConstrains);
  }

  private RequestImpl filterValidFields(Set<ConstraintViolation<RequestImpl>> violations) {
    try {
      Set<String> invalidFields = violations.stream()
        .map(violation -> violation.getPropertyPath().toString())
        .collect(Collectors.toSet());

      // Clone the current object
      RequestImpl filteredObject = this.getClass().getDeclaredConstructor().newInstance();

      for (Field field : this.getClass().getDeclaredFields()) {
        field.setAccessible(true); // Allow access to private fields
        if (!invalidFields.contains(field.getName())) {
          field.set(filteredObject, field.get(this)); // Copy valid fields
        }
      }

      return filteredObject;
    } catch (Exception e) {
      throw new RuntimeException("Error filtering valid fields", e);
    }
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
