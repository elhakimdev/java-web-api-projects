package com.sass.erp.finance.cash.api_service.http.requests.concerns;

public enum SortDirectionRequest {
  ASCENDING("asc"),
  DESCENDING("desc");

  private final String value;

  SortDirectionRequest(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
