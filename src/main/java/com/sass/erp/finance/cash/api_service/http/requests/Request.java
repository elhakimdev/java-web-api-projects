package com.sass.erp.finance.cash.api_service.http.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sass.erp.finance.cash.api_service.http.requests.impl.RoleRequestImpl;
import com.sass.erp.finance.cash.api_service.http.requests.impl.UserRequestImpl;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = UserRequestImpl.class, name = "userRequest"),
  @JsonSubTypes.Type(value = RoleRequestImpl.class, name = "roleRequest"),
})
public interface Request {
}
