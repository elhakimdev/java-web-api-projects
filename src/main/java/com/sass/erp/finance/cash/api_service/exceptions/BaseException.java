package com.sass.erp.finance.cash.api_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseException extends Exception {
    protected Integer errorCode;
    protected String errorType;
}
