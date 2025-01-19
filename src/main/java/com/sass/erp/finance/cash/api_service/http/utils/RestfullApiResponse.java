package com.sass.erp.finance.cash.api_service.http.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestfullApiResponse<T> {
    protected Optional<T> data;
    protected String message;
    protected RestfullApiResponseStatus status;
    protected Optional<HttpStatus> statusCode;
    protected Optional<RestfullApiResponseError> error;
    protected LocalDateTime timestamp;
}
