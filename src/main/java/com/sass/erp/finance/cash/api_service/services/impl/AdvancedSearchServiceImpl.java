package com.sass.erp.finance.cash.api_service.services.impl;

import com.sass.erp.finance.cash.api_service.http.requests.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.http.requests.concerns.FilterOperatorRequest;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.services.AdvancedSearchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
    this.logger.info("search request : {}", searchRequest);
    this.logger.info("pageable request : {}", pageable);

    Specification<T> spec = Specification.where(null);

    // Apply filters to the query specification
    if (searchRequest.getFilters() != null) {
      for (AdvanceSearchRequest.Filter filter : searchRequest.getFilters()) {
        spec = spec.and(buildFilter(filter));
      }
    }

    // Apply search value
    if (searchRequest.getSearch().getValue() != null && !searchRequest.getSearch().getValue().isEmpty()) {
      spec = spec.and(searchByValue(searchRequest.getSearch().getValue()));
    }

    // Apply sorting (if any)
    if (searchRequest.getSort() != null) {
      for (AdvanceSearchRequest.Sort sort : searchRequest.getSort()) {
        // You may need a utility method to convert the sort direction string ("asc" or "desc")
        spec = spec.and(buildSort(sort));
      }
    }

    // Return paginated results
    return repository.findAll(spec, transformPage(pageable));
  }

  private Specification<T> buildFilter(AdvanceSearchRequest.Filter filter) {
    return (root, query, criteriaBuilder) -> {
      Path<Object> path = root.get(filter.getField());
      switch (filter.getOperator()) {
        case FilterOperatorRequest.GREATER_THAN_OR_EQUAL:
          return criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.EQUAL:
          return criteriaBuilder.equal(path, filter.getValue());
        case FilterOperatorRequest.IN:
          CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(path);
          ((List<?>) filter.getValue()).forEach(inClause::value);
          return inClause;
        // Add more operators here...
        default:
          return null;
      }
    };
  }

  private Specification<T> searchByValue(String searchValue) {
    return (root, query, criteriaBuilder) -> {
      // Example for a 'search' implementation on the 'name' field (or other relevant fields)
      return criteriaBuilder.like(root.get("name"), "%" + searchValue + "%");
    };
  }

  private Specification<T> buildSort(AdvanceSearchRequest.Sort sort) {
    return (root, query, criteriaBuilder) -> {
      // Set the sorting order for the query (this doesn't need to return a Predicate)
      assert query != null;
      query.orderBy(
        "asc".equalsIgnoreCase(String.valueOf(sort.getDirection()))
          ? criteriaBuilder.asc(root.get(sort.getField()))
          : criteriaBuilder.desc(root.get(sort.getField()))
      );

      return criteriaBuilder.conjunction(); // No filtering, just need to return a valid Predicate
    };
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
