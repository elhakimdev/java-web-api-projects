package com.sass.erp.finance.cash.api_service.http.resources;

import com.sass.erp.finance.cash.api_service.enums.UserResourceField;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;

import java.util.HashMap;

public class UserResource extends Resource {
  private final UserEntity user;

  public UserResource(UserEntity user){
    this.user = user;
  }

  public HashMap<String, Object> toResponse(){
    HashMap<String, Object> resourceMap = new HashMap<>();
    resourceMap.put(String.valueOf(UserResourceField.ID), this.user.getIdentifier().getUuid());
    resourceMap.put(String.valueOf(UserResourceField.EXTERNAL_REF_ID), this.user.getExternalIdentifier().getExternalId());
    resourceMap.put(String.valueOf(UserResourceField.SYSTEM_REF_ID), this.user.getExternalIdentifier().getSystemRefId());
    resourceMap.put(String.valueOf(UserResourceField.DISPLAY_REF_ID), this.user.getExternalIdentifier().getDisplayId());
    resourceMap.put(String.valueOf(UserResourceField.USERNAME), this.user.getUserUsername());
    resourceMap.put(String.valueOf(UserResourceField.EMAIL), this.user.getUserEmail());
    resourceMap.put(String.valueOf(UserResourceField.IS_ACTIVE), this.user.getUserIsActive());
    resourceMap.put(String.valueOf(UserResourceField.LAST_LOGIN_AT), this.user.getUserLastLoginAt());
    resourceMap.put(String.valueOf(UserResourceField.EMAIL_VERIFIED_AT), this.user.getUserEmailVerifiedAt());
    resourceMap.put(String.valueOf(UserResourceField.IS_LOCKED), this.user.getUserIsLocked());
    resourceMap.put(String.valueOf(UserResourceField.IS_VERIFIED), this.user.getUserIsVerified());
    resourceMap.put(String.valueOf(UserResourceField.ROLES), this.user.getUserRoles());
    resourceMap.put(String.valueOf(UserResourceField.CREATED_AT), this.user.getTimeStamp().getCreatedAt());
    resourceMap.put(String.valueOf(UserResourceField.CREATED_BY), this.user.getAuditLog().getCreatedBy());
    resourceMap.put(String.valueOf(UserResourceField.UPDATED_AT), this.user.getTimeStamp().getUpdatedAt());
    resourceMap.put(String.valueOf(UserResourceField.LAST_UPDATED_BY), this.user.getAuditLog().getLastUpdatedBy());
    return resourceMap;
  }
}
