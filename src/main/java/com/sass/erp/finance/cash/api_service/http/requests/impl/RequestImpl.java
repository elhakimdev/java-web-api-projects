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

  /**
   * Validator factory
   */
  private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

  /**
   * Validator instance.
   */
  private static final Validator validator = factory.getValidator();

  /**
   * Determine that action was authorized.
   *
   * @return Boolean represent authorization.
   */
  protected abstract boolean authorize();

  /**
   * Check authorization before performing action.
   *
   * @throws UnauthorizedRequestException Exception will be thrown.
   */
  protected void checkAuthorization() throws UnauthorizedRequestException {
    if (!authorize()) {
      throw new UnauthorizedRequestException(
        "Unauthorized to perform this action due the restriction policies",
        ApplicationExceptionTypeConstant.GENERAL_ERROR,
        ApplicationExceptionCodeConstant.OPERATION_NOT_ALLOWED,
        List.of("Require fulfill personal identify", "kyc required", "should be logged in first", "missing scope")
      );
    }
  }

  /**
   * Check the violations constraints.
   *
   * @param violations Set of violated constrains.
   */
  protected void checkViolations(Set<ConstraintViolation<RequestImpl>> violations) {
    if(!violations.isEmpty()) {
      Map<String, List<String>> errors = violations.stream().collect(
        Collectors.groupingBy(v -> v.getPropertyPath().toString(), Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList()))
      );
      throw  new ValidationFailedException(
        "Validation failed",
        ApplicationExceptionTypeConstant.VALIDATION_ERROR,
        ApplicationExceptionCodeConstant.INVALID_REQUEST,
        errors
      );
    }
  }

  /**
   * Processing request validation.
   * @param groups Validation groups.
   * @return The request instance.
   * @throws ValidationFailedException Exception.
   */
  protected Set<ConstraintViolation<RequestImpl>> processValidation(Class<?> ...groups) throws ValidationFailedException {
    this.checkAuthorization();
    return validator.validate(this, groups);
  }

  @SuppressWarnings("unchecked")
  protected <Req extends Request> Req validateThenFilter(Class<?> ...groups) throws ValidationFailedException {
    Set<ConstraintViolation<RequestImpl>> fields = this.validate(groups);
    this.checkViolations(fields);
    return (Req) this.filterValidFields(fields);
  }

  @Override
  public Set<ConstraintViolation<RequestImpl>> validate() throws ValidationFailedException {
    return this.processValidation(); // Calls overloaded method without parameters
  }

  @Override
  public Set<ConstraintViolation<RequestImpl>> validate(Class<?>... groups) throws ValidationFailedException {
    return this.processValidation(groups);
  }

  /**
   * Get the validated fields.
   *
   * @return The validated field.
   * @param <Req> The request instance.
   * @throws ValidationFailedException Exception throw when violations happens.
   */
  @Override
  public <Req extends Request> Req validated() throws ValidationFailedException {
    return this.validateThenFilter();
  }

  /**
   * Get the validated fields.
   *
   * @return The validated field.
   * @param <Req> The request instance.
   * @throws ValidationFailedException Exception throw when violations happens.
   */
  @Override
  public <Req extends Request> Req validated(Class<?> ...groups) throws ValidationFailedException {
    return this.validateThenFilter(groups);
  }

  /**
   * Filtering valid fields to be used to persist entity.
   *
   * @param violations The violations.
   * @return The request implementation instance.
   */
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

  /**
   * For create validation groups.
   */
  public interface Create {}

  /**
   * For update validation groups.
   */
  public interface Update {}
}
