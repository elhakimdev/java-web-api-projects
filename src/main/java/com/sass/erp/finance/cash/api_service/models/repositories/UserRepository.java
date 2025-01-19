package com.sass.erp.finance.cash.api_service.models.repositories;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;

public interface UserRepository extends BaseRepository<UserEntity, EmbeddedUUID> {
}

