package com.sass.erp.finance.cash.api_service.http.utils;
import java.util.List;

public record RestfullApiResponseError<E>(
  String message,
  String type,
  String code,
  String traceId,
  List<E> details
) {
}
