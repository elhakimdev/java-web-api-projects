package com.sass.erp.finance.cash.api_service.services.impl;
import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.specifications.AdvanceFilterSpecification;
import com.sass.erp.finance.cash.api_service.models.specifications.AdvanceSearchSpecification;
import com.sass.erp.finance.cash.api_service.models.specifications.AdvanceSortSpecification;
import com.sass.erp.finance.cash.api_service.services.AdvancedSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdvancedSearchServiceImpl<T extends BaseEntity, ID extends EmbeddedIdentifier> implements AdvancedSearchService<T, ID> {
  private final Logger logger = LoggerFactory.getLogger(AdvancedSearchServiceImpl.class);

  /**
   * Search entity with advanced search.
   *
   * @param repository the repository.
   * @param searchRequest The request body for search with advanced searching.
   * @param pageable      The pagination implementation.
   * @return The paginated entity as paginated collections entity.
   */
  @Override
  public Page<T> search(BaseRepository<T, ID> repository, AdvanceSearchRequest searchRequest, Pageable pageable) {
    Specification<T> spec = Specification.where(null);
    // Apply sorting (if any)  to the query specification.
    if (searchRequest.getSort() != null) {
      spec = spec.and(AdvanceSortSpecification.withSort(searchRequest));
    }

    // Apply filters (if any) to the query specification.
    if (searchRequest.getFilters() != null) {
      spec = spec.and(AdvanceFilterSpecification.withFilter(searchRequest));
    }

    // Apply search (if any) value to the query specification.
    if (searchRequest.getSearch() != null && searchRequest.getSearch().getValue() != null && !searchRequest.getSearch().getValue().isEmpty()) {
      spec = spec.and(AdvanceSearchSpecification.withSearch(searchRequest));
    }

    // Return paginated results
    return repository.findAll(spec, transformPage(pageable));
  }

  /**
   * Adjust the page index from 1-based to 0-based for Spring Data Pageable.
   *
   * @param pageable The Pageable object with 1-based index.
   * @return Pageable with adjusted 0-based index.
   */
  private Pageable transformPage(Pageable pageable) {
    int pageIndex = pageable.getPageNumber();
    if (pageIndex > 0) {
      // Adjust for 1-based to 0-based
      pageIndex -= 1;
    }
    return PageRequest.of(pageIndex, pageable.getPageSize(), pageable.getSort());
  }
}
