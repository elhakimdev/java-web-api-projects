package com.sass.erp.finance.cash.api_service.http.utils;

import com.sass.erp.finance.cash.api_service.exceptions.BaseException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RestfullApiResponseFactory {
  public static <T, E> RestfullApiResponse<T, E> success(
    T data,
    String message,
    HttpStatus httpStatus
  ) {

    RestfullApiResponse<T, E> successResponse = new RestfullApiResponse<>();
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

  public static <T, E extends BaseException> RestfullApiResponse<T, E> failed(
    Exception error,
    String message,
    String code,
    String traceId,
    HttpStatus httpStatus,
    List<Object> details
  ) {
    RestfullApiResponse<T, E> errorResponse = new RestfullApiResponse<T, E>() {
    };

    RestfullApiResponseError<E> errorMapping = new RestfullApiResponseError<E>(
      error.getClass().getCanonicalName(),
      code,
      traceId,
      details
    );

    errorResponse.setMessage(message);
    errorResponse.setStatus(RestfullApiResponseStatus.FAILED);
    errorResponse.setStatusCode(httpStatus.value());
    errorResponse.setStatusText(httpStatus.getReasonPhrase());
    errorResponse.setError(errorMapping);
    errorResponse.setData(null);
    errorResponse.setTimestamp(LocalDateTime.now());
    return errorResponse;
  }
}
