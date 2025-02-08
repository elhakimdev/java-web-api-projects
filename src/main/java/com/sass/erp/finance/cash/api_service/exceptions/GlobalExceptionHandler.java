package com.sass.erp.finance.cash.api_service.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sass.erp.finance.cash.api_service.exceptions.runtime.UnauthorizedRequestException;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
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
    log.error("TraceLogID: {}, code: {}", traceLogId, code);

    RestfullApiResponse<Object> failed = RestfullApiResponseFactory.failed(
      exception,
      traceLogId,
      exception.getMessage(),
      httpStatus,
      details
    );

    return ResponseEntity.status(httpStatus).body(failed);
  }

  ResponseEntity<RestfullApiResponse<Object>> handleUnauthorizedRequestException(HttpServletRequest req, UnauthorizedRequestException unauthorizedRequestException) {
    log.error("HttpServletRequest Info: {}, UnauthorizedRequestException: ", req, unauthorizedRequestException);
    return this.renderException(unauthorizedRequestException, List.of(), exceptionStatusMapping.get(UnauthorizedRequestException.class));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException httpMessageNotReadableException) {
    log.error("HttpServletRequest Info: {}, HttpMessageNotReadableException: ", req, httpMessageNotReadableException);
    return this.renderException(httpMessageNotReadableException, List.of(), exceptionStatusMapping.get(HttpMessageNotReadableException.class));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException illegalArgumentException) {
    log.error("HttpServletRequest Info: {}, IllegalArgumentException: ", req, illegalArgumentException);
    return this.renderException(illegalArgumentException, List.of(), exceptionStatusMapping.get(IllegalArgumentException.class));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  ResponseEntity<RestfullApiResponse<Object>> handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException entityNotFoundException) {
    log.error("HttpServletRequest Info: {}, EntityNotFoundException: ", req, entityNotFoundException);
    return this.renderException(entityNotFoundException, List.of(), exceptionStatusMapping.get(EntityNotFoundException.class));
  }


  static {
    exceptionStatusMapping.put(UnauthorizedRequestException.class, HttpStatus.UNAUTHORIZED);
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
