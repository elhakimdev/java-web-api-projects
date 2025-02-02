package com.sass.erp.finance.cash.api_service.http.requests.impl.concerns;

public enum FilterOperatorRequest {
  LESS_THAN("<"),
  LESS_THAN_OR_EQUAL("<="),
  GREATER_THAN(">"),
  GREATER_THAN_OR_EQUAL(">="),
  EQUAL("="),
  NOT_EQUAL("!="),
  LIKE("like"),
  NOT_LIKE("not like"),
  ILIKE("ilike"),
  NOT_ILIKE("not ilike"),
  IN("in"),
  NOT_IN("not in"),
  ALL_IN("all in"),
  ANY_IN("any in");

  private final String value;

  FilterOperatorRequest(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
