package com.sass.erp.finance.cash.api_service.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  protected ResponseEntity<RestfullApiResponse<ObjectUtils.Null, BaseException>> renderException(
    Exception exception,
    List<Object> details,
    HttpStatus httpStatus
  ) {

    String traceLogId = UUID.randomUUID().toString();
    String code = exceptionCodeMapping.get(exception.getClass());
    log.error("TraceLogID: {}, Exception message: {}", traceLogId, exception.getMessage());

    RestfullApiResponse<ObjectUtils.Null, BaseException> response = RestfullApiResponseFactory.failed(
      exception,
      exception.getMessage(),
      code,
      traceLogId,
      httpStatus,
      details
    );

    return ResponseEntity
      .status(httpStatus)
      .body(response);
  }

  protected

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<RestfullApiResponse<ObjectUtils.Null, BaseException>> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException ex) {
    return this.renderException(ex, List.of(), exceptionStatusMapping.get(HttpMessageNotReadableException.class));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<RestfullApiResponse<ObjectUtils.Null, BaseException>> handleHttpMessageNotReadableException(HttpServletRequest req, IllegalArgumentException ex) {
    return this.renderException(ex, List.of(), exceptionStatusMapping.get(IllegalArgumentException.class));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  ResponseEntity<RestfullApiResponse<ObjectUtils.Null, BaseException>> handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
    return this.renderException(ex, List.of(), exceptionStatusMapping.get(EntityNotFoundException.class));
  }


  static {
    exceptionStatusMapping.put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(JsonProcessingException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
    exceptionStatusMapping.put(ConstraintViolationException.class, HttpStatus.UNPROCESSABLE_ENTITY);
    exceptionStatusMapping.put(NoResourceFoundException.class, HttpStatus.NOT_FOUND);
    exceptionStatusMapping.put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND);
    exceptionStatusMapping.put(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
  }

  static {
    exceptionCodeMapping.put(HttpMessageNotReadableException.class, "");
    exceptionCodeMapping.put(JsonProcessingException.class, "");
    exceptionCodeMapping.put(IllegalArgumentException.class, "");
    exceptionCodeMapping.put(ConstraintViolationException.class, "");
    exceptionCodeMapping.put(NoResourceFoundException.class, "No_Resource_Found_Exception");
    exceptionCodeMapping.put(NoHandlerFoundException.class, "No_Handler_Found_Exception");
    exceptionCodeMapping.put(EntityNotFoundException.class, "Entity_Not_Found_Exception");
  }
}
