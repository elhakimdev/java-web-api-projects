package com.sass.erp.finance.cash.api_service.http.utils;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RestfullApiResponseFactory {
    public static <T> RestfullApiResponse<T> success(
            T data,
            String message,
            Optional<HttpStatus> statusCode
    ) {
        RestfullApiResponse<T> successResponse = new RestfullApiResponse<T>() {

        };
        successResponse.setData(Optional.ofNullable(data));
        successResponse.setMessage(message);
        successResponse.setStatus(RestfullApiResponseStatus.SUCCESS);
        successResponse.setError(Optional.empty());
        if(statusCode.isEmpty()){
            successResponse.setStatusCode(Optional.of(HttpStatus.OK));
        }
        if(statusCode.isPresent()) {
            successResponse.setStatusCode(statusCode);
        }
        successResponse.setTimestamp(LocalDateTime.now());
        return successResponse;
    }

    public static <E> RestfullApiResponse<?> failed(
            E error,
            String message,
            Optional<HttpStatus> statusCode
    ) {
        RestfullApiResponse successResponse = new RestfullApiResponse<>() {

        };
        successResponse.setData(Optional.empty());
        successResponse.setMessage(message);
        successResponse.setStatus(RestfullApiResponseStatus.SUCCESS);
        successResponse.setError(Optional.ofNullable(error));
        if(statusCode.isEmpty()){
            successResponse.setStatusCode(Optional.of(HttpStatus.BAD_REQUEST));
        }
        if(statusCode.isPresent()) {
            successResponse.setStatusCode(statusCode);
        }
        successResponse.setTimestamp(LocalDateTime.now());
        return successResponse;
    }
}
