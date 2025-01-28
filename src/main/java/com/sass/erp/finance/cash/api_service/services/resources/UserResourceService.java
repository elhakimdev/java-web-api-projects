package com.sass.erp.finance.cash.api_service.services.resources;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;

public interface UserResourceService<
    U extends UserEntity, E extends EmbeddedIdentifier
  > extends RestfullApiService<U, E> {
}
