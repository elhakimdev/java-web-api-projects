package com.sass.erp.finance.cash.api_service.http.utils;

import com.sass.erp.finance.cash.api_service.exceptions.BaseException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public class RestfullApiResponseFactory {
  public static <T> RestfullApiResponse<T> success(
    T data,
    String message,
    HttpStatus httpStatus
  ) {
    RestfullApiResponse<T> successResponse = new RestfullApiResponse<T>() {

    };
    successResponse.setMessage(message);
    successResponse.setStatus(RestfullApiResponseStatus.SUCCESS);
    successResponse.setStatusCode(httpStatus.value());
    successResponse.setStatusText(httpStatus.getReasonPhrase());
    successResponse.setError(Optional.empty());
    successResponse.setData(Optional.ofNullable(data));
    successResponse.setTimestamp(LocalDateTime.now());
    return successResponse;
  }

  public static <E extends BaseException> RestfullApiResponse<E> failed(
    Exception error,
    String message,
    String code,
    String traceId,
    HttpStatus httpStatus
  ) {
    RestfullApiResponse<E> errorResponse = new RestfullApiResponse<E>() {
    };

    RestfullApiResponseError errorMap = new RestfullApiResponseError();
    errorMap.setErrorCode(code);
    errorMap.setErrorTraceId(traceId);
    errorMap.setErrorMessage(error.getMessage());
    errorMap.setErrorType(error.getClass().getCanonicalName());

    errorResponse.setMessage(message);
    errorResponse.setStatus(RestfullApiResponseStatus.FAILED);
    errorResponse.setStatusCode(httpStatus.value());
    errorResponse.setStatusText(httpStatus.getReasonPhrase());
    errorResponse.setError(Optional.of(errorMap));
    errorResponse.setData(Optional.empty());
    errorResponse.setTimestamp(LocalDateTime.now());
    return errorResponse;
  }
}
