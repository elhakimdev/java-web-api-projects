package com.sass.erp.finance.cash.api_service.services.resources;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;

public interface RoleResourceService<
    U extends RoleEntity, E extends EmbeddedIdentifier
  > extends RestfullApiService<U, E> {
}
