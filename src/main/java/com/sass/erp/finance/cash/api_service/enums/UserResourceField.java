package com.sass.erp.finance.cash.api_service.enums;

import lombok.Getter;

@Getter
public enum UserResourceField {
  ID("id"),
  EXTERNAL_REF_ID("externalRefId"),
  SYSTEM_REF_ID("systemRefId"),
  DISPLAY_REF_ID("displayRefId"),
  USERNAME("username"),
  EMAIL("email"),
  IS_ACTIVE("isActive"),
  LAST_LOGIN_AT("lastLoginAt"),
  EMAIL_VERIFIED_AT("emailVerifiedAt"),
  IS_LOCKED("isLocked"),
  IS_VERIFIED("isVerified"),
  ROLES("roles"),
  CREATED_AT("createdAt"),
  UPDATED_AT("updatedBy"),
  CREATED_BY("createdBy"),
  LAST_UPDATED_BY("lastUpdatedBy");

  private final String key;

  UserResourceField(String key){
    this.key = key;
  }

  @Override
  public String toString(){
    return this.key;
  }
}
