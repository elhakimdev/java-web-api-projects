package com.sass.erp.finance.cash.api_service.models.repositories;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;

public interface RoleRepository<
    R extends RoleEntity,
    ID extends EmbeddedIdentifier
  > extends BaseRepository<R, ID> {
}

