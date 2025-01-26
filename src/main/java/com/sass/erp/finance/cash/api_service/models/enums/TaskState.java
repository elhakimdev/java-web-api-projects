package com.sass.erp.finance.cash.api_service.models.enums;

import lombok.Getter;

@Getter
public enum TaskState {
  PENDING("pending"),
  IN_PROGRESS("in-progress"),
  COMPLETED("completed"),
  FAILED("failed");

  private final String state;
  TaskState( String state) {
    this.state = state;
  }

  @Override
  public String toString(){
    return this.state;
  }
}
