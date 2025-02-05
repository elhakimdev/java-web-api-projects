package com.sass.erp.finance.cash.api_service.http.requests.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestImpl extends RequestImpl {
  private String name;
  private String description;
  private String guardName;

  @Override
  protected boolean authorize() {
    return true;
  }
}
