package com.sass.erp.finance.cash.api_service.http.resources;

import com.sass.erp.finance.cash.api_service.enums.UserResourceField;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserResource extends Resource<UserEntity> {
  /**
   * The Resource mapping
   * @return USer Resource
   */
  protected HashMap<String, Object> toResource(){
    HashMap<String, Object> resourceMap = new HashMap<>();
    resourceMap.put(String.valueOf(UserResourceField.ID), this.entity.getIdentifier().getUuid());
    resourceMap.put(String.valueOf(UserResourceField.EXTERNAL_REF_ID), this.entity.getExternalIdentifier().getExternalId());
    resourceMap.put(String.valueOf(UserResourceField.SYSTEM_REF_ID), this.entity.getExternalIdentifier().getSystemRefId());
    resourceMap.put(String.valueOf(UserResourceField.DISPLAY_REF_ID), this.entity.getExternalIdentifier().getDisplayId());
    resourceMap.put(String.valueOf(UserResourceField.USERNAME), this.entity.getUsername());
    resourceMap.put(String.valueOf(UserResourceField.EMAIL), this.entity.getEmail());
    resourceMap.put(String.valueOf(UserResourceField.IS_ACTIVE), this.entity.getIsActive());
    resourceMap.put(String.valueOf(UserResourceField.LAST_LOGIN_AT), this.entity.getLastLoginAt());
    resourceMap.put(String.valueOf(UserResourceField.EMAIL_VERIFIED_AT), this.entity.getEmailVerifiedAt());
    resourceMap.put(String.valueOf(UserResourceField.IS_LOCKED), this.entity.getIsLocked());
    resourceMap.put(String.valueOf(UserResourceField.IS_VERIFIED), this.entity.getIsVerified());
    resourceMap.put(String.valueOf(UserResourceField.ROLES), this.entity.getRoles());
    resourceMap.put(String.valueOf(UserResourceField.CREATED_AT), this.entity.getTimeStamp().getCreatedAt());
    resourceMap.put(String.valueOf(UserResourceField.CREATED_BY), this.entity.getAuditLog().getCreatedBy());
    resourceMap.put(String.valueOf(UserResourceField.UPDATED_AT), this.entity.getTimeStamp().getUpdatedAt());
    resourceMap.put(String.valueOf(UserResourceField.LAST_UPDATED_BY), this.entity.getAuditLog().getLastUpdatedBy());
    return resourceMap;
  }


  /**
   * The collection mapping for given entity.
   *
   * @return Map of collection entity.
   */
  protected HashMap<String, Object> toCollections() {
    HashMap<String, Object> resourceCollectionMap = new HashMap<>();

    resourceCollectionMap.put(String.valueOf(UserResourceField.ID), this.entity.getIdentifier().getUuid());
    resourceCollectionMap.put(String.valueOf(UserResourceField.DISPLAY_REF_ID), this.entity.getExternalIdentifier().getDisplayId());
    resourceCollectionMap.put(String.valueOf(UserResourceField.USERNAME), this.entity.getUsername());
    resourceCollectionMap.put(String.valueOf(UserResourceField.EMAIL), this.entity.getEmail());

    return resourceCollectionMap;
  }
}
