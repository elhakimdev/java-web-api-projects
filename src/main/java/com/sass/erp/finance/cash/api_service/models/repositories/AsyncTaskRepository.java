package com.sass.erp.finance.cash.api_service.models.repositories;

import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.generics.system.AsyncTaskEntity;

import java.util.UUID;

public interface AsyncTaskRepository extends BaseRepository<AsyncTaskEntity, EmbeddedIdentifier> {
}
