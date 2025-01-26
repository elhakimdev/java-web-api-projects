package com.sass.erp.finance.cash.api_service.exceptions;

import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Logger instance.
   */
  protected Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Exception to http status maps.
   */
  private static final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMapping = new HashMap<>();

  /**
   * Render exception as json response
   *
   * @param exception The exception.
   * @param httpStatus The HttpStatus.
   * @return The Json Response.
   */
  protected ResponseEntity<RestfullApiResponse<BaseException>> renderException(
    Exception exception,
    HttpStatus httpStatus
  ) {

    String traceLogId = UUID.randomUUID().toString();

    logger.error("TraceLogID: {}, Exception caught: {}", traceLogId, exception.getMessage(), exception);

    RestfullApiResponse<BaseException> response = RestfullApiResponseFactory.failed(
      exception,
      exception.getMessage(),
      "0000X00000",
      traceLogId,
      httpStatus
    );

    return ResponseEntity
      .status(httpStatus)
      .body(response);
  }

  /**
   * General Exception Handler
   *
   * Handle all exception and return json response.
   *
   * @param exception
   * @return
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<RestfullApiResponse<BaseException>> handleGenericException(Exception exception) {

    HttpStatus httpStatus = exceptionStatusMapping.getOrDefault(
      exception.getClass(),
      HttpStatus.INTERNAL_SERVER_ERROR
    );

    return this.renderException(
      exception,
      httpStatus
    );
  }

  static {
    exceptionStatusMapping.put(IllegalArgumentException.class, HttpStatus.INTERNAL_SERVER_ERROR);
    exceptionStatusMapping.put(ConstraintViolationException.class, HttpStatus.UNPROCESSABLE_ENTITY);
    exceptionStatusMapping.put(NoResourceFoundException.class, HttpStatus.NOT_FOUND);
    exceptionStatusMapping.put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND);
  }
}
