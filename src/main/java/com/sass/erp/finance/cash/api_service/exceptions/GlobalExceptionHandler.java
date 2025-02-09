package com.sass.erp.finance.cash.api_service.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.ApplicationException;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.UnauthorizedRequestException;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.ValidationFailedException;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleDatabaseException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  /**
   * Exception to http status maps.
   */
  private static final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMapping = new HashMap<>();

  private static final Map<Class<? extends Exception>, String> exceptionCodeMapping = new HashMap<>();

  /**
   * Render exception as json response
   *
   * @param exception The exception.
   * @param httpStatus The HttpStatus.
   * @return The Json Response.
   */
  protected ResponseEntity<RestfullApiResponse<Object>> renderException(
    Exception exception,
    List<Object> details,
    HttpStatus httpStatus
  ) {
    String traceLogId = UUID.randomUUID().toString();
    String code = exceptionCodeMapping.get(exception.getClass());
    log.error("TraceLogID: {}, code: {}", traceLogId, exception instanceof ApplicationException ? ((ApplicationException) exception).getCode() : code);

    RestfullApiResponse<Object> failed = RestfullApiResponseFactory.failed(
      exception,
      traceLogId,
      exception.getMessage(),
      httpStatus,
      details
    );

    return ResponseEntity.status(httpStatus).body(failed);
  }

  @ExceptionHandler(ValidationFailedException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleValidationFailedException(HttpServletRequest req, ValidationFailedException validationFailedException) {
    log.error("HttpServletRequest Info: {}, ValidationFailedException Details: {}", req, validationFailedException.getDetails());
    return this.renderException(validationFailedException, List.of(), exceptionStatusMapping.get(ValidationFailedException.class));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException dataIntegrityViolationException) {
    log.error("HttpServletRequest Info: {}, DataIntegrityViolationException: Details: {}", req, dataIntegrityViolationException.toString());
    return this.renderException(dataIntegrityViolationException, List.of(), exceptionStatusMapping.get(DataIntegrityViolationException.class));
  }

  @ExceptionHandler(OracleDatabaseException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleOracleDatabaseException(HttpServletRequest req, OracleDatabaseException oracleDatabaseException) {
    log.error("HttpServletRequest Info: {}, OracleDatabaseException: Details: {}", req, oracleDatabaseException.toString());
    return this.renderException(oracleDatabaseException, List.of(), exceptionStatusMapping.get(OracleDatabaseException.class));
  }


  @ExceptionHandler(UnauthorizedRequestException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleUnauthorizedRequestException(HttpServletRequest req, UnauthorizedRequestException unauthorizedRequestException) {
    log.error("HttpServletRequest Info: {}, UnauthorizedRequestException Details: {}", req, unauthorizedRequestException.getDetails());
    return this.renderException(unauthorizedRequestException, List.of(), exceptionStatusMapping.get(UnauthorizedRequestException.class));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException httpMessageNotReadableException) {
    log.error("HttpServletRequest Info: {}, HttpMessageNotReadableException: {}", req.toString(), httpMessageNotReadableException.toString());
    return this.renderException(httpMessageNotReadableException, List.of(), exceptionStatusMapping.get(HttpMessageNotReadableException.class));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException illegalArgumentException) {
    log.error("HttpServletRequest Info: {}, IllegalArgumentException: {}", req.toString(), illegalArgumentException.toString());
    return this.renderException(illegalArgumentException, List.of(), exceptionStatusMapping.get(IllegalArgumentException.class));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException entityNotFoundException) {
    log.error("HttpServletRequest Info: {}, EntityNotFoundException: {}", req.toString(), entityNotFoundException.toString());
    return this.renderException(entityNotFoundException, List.of(), exceptionStatusMapping.get(EntityNotFoundException.class));
  }


  static {
    exceptionStatusMapping.put(OracleDatabaseException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(UnauthorizedRequestException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(JsonProcessingException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(ConstraintViolationException.class, HttpStatus.UNPROCESSABLE_ENTITY);
    exceptionStatusMapping.put(ValidationFailedException.class, HttpStatus.UNPROCESSABLE_ENTITY);
    exceptionStatusMapping.put(NoResourceFoundException.class, HttpStatus.NOT_FOUND);
    exceptionStatusMapping.put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND);
    exceptionStatusMapping.put(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
  }
}
