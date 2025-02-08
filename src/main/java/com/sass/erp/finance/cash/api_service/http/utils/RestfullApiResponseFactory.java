package com.sass.erp.finance.cash.api_service.http.utils;

import com.sass.erp.finance.cash.api_service.exceptions.runtime.ApplicationException;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

public class RestfullApiResponseFactory {
  public static <T> RestfullApiResponse<T> success(
    T data,
    String message,
    HttpStatus httpStatus
  ) {

    RestfullApiResponse<T> successResponse = new RestfullApiResponse<>();
    RestfullApiResponseData<T> responseData = new RestfullApiResponseData<>(data);

    successResponse.setMessage(message);
    successResponse.setStatus(RestfullApiResponseStatus.SUCCESS);
    successResponse.setStatusCode(httpStatus.value());
    successResponse.setStatusText(httpStatus.getReasonPhrase());
    successResponse.setError(null);
    successResponse.setData(responseData.data());
    successResponse.setTimestamp(LocalDateTime.now());
    return successResponse;
  }
  public static <T, Ex extends Exception> RestfullApiResponse<T> failed(
    Ex exception,
    String traceId,
    String message,
    HttpStatus httpStatus,
    List<Object> details
  ) {
    RestfullApiResponse<T> errorResponse = new RestfullApiResponse<>() {};

    RestfullApiResponseError<Ex> appGenericErrorResponse = null;

    RestfullApiResponseError<ApplicationException> appExceptionErrorResponse = null;

    if (exception instanceof ApplicationException appException) {
      appExceptionErrorResponse = appException.toResponse(traceId);
    } else if (exception instanceof RuntimeException) {
      appGenericErrorResponse = new RestfullApiResponseError<>("RUNTIME_EXCEPTION", "0x000", traceId, details);
    } else {
      appGenericErrorResponse = new RestfullApiResponseError<>("UNKNOWN_EXCEPTION", "0x000", traceId, details);
    }

    errorResponse.setMessage(message);
    errorResponse.setStatus(RestfullApiResponseStatus.FAILED);
    errorResponse.setStatusCode(httpStatus.value());
    errorResponse.setStatusText(httpStatus.getReasonPhrase());
    errorResponse.setError(exception instanceof ApplicationException ? appExceptionErrorResponse : appGenericErrorResponse);
    errorResponse.setData(null);
    errorResponse.setTimestamp(LocalDateTime.now());

    return errorResponse;
  }
}
