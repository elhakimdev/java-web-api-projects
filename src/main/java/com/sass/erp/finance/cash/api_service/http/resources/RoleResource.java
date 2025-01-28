package com.sass.erp.finance.cash.api_service.http.resources;

import com.sass.erp.finance.cash.api_service.enums.UserResourceField;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RoleResource extends Resource<RoleEntity> {
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

    return resourceCollectionMap;
  }
}
