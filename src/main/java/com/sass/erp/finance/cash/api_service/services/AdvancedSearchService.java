package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdvancedSearchService<T extends BaseEntity, ID extends EmbeddedIdentifier> {
  /**
   * Search entity with advanced search.
   *
   * @param searchRequest The request body for search with advanced searching.
   * @param pageable      The pagination implementation.
   * @return The paginated entity as paginated collections entity.
   */
  Page<T> search(BaseRepository<T, ID> repository, AdvanceSearchRequest searchRequest, Pageable pageable);
}
