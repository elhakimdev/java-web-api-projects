package com.sass.erp.finance.cash.api_service.http.requests.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@Getter
@Setter
@JsonTypeName("userRequest")
public class UserRequestImpl extends RequestImpl {
  private String username;
  private String email;
  private String password;
}
