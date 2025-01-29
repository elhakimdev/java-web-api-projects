package com.sass.erp.finance.cash.api_service.models.specifications;

import com.sass.erp.finance.cash.api_service.http.requests.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.http.requests.concerns.FilterOperatorRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AdvanceFilterSpecification {
  public static <T> Specification<T> withFilter(AdvanceSearchRequest searchRequest) {

    return (root, query, criteriaBuilder) -> {

      Specification<T> internalSpec = Specification.where((root1, query1, internalCriteriaBuilder) -> internalCriteriaBuilder.conjunction());

      for (AdvanceSearchRequest.Filter filter : searchRequest.getFilters()) {
        if(isFilterable(searchRequest, filter.getField())) {
          internalSpec = internalSpec.and(withFilterMatchSpec(filter));
        }
      }

      return internalSpec.toPredicate(root, query, criteriaBuilder);
    };
  }

  // Checks if a field is filterable
  private static boolean isFilterable(AdvanceSearchRequest searchRequest, String field) {
    // If filterableBy is empty or null, assume all fields are filterable
    return searchRequest.getFilterableBy() == null || searchRequest.getFilterableBy().isEmpty() || searchRequest.getFilterableBy().contains(field);
  }

  private static <T> Specification<T> withFilterMatchSpec(AdvanceSearchRequest.Filter filter) {
    return (root, query, criteriaBuilder) -> {
      Path<Object> path = root.get(filter.getField());

      switch (filter.getOperator()) {
        case FilterOperatorRequest.GREATER_THAN_OR_EQUAL:
          return criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.GREATER_THAN:
          return criteriaBuilder.greaterThan(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.LESS_THAN_OR_EQUAL:
          return criteriaBuilder.lessThanOrEqualTo(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.LESS_THAN:
          return criteriaBuilder.lessThan(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.EQUAL:
          return criteriaBuilder.equal(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.NOT_EQUAL:
          return criteriaBuilder.notEqual(path.as(String.class), filter.getValue().toString());
        case FilterOperatorRequest.LIKE:
          return criteriaBuilder.like(path.as(String.class), "%" + filter.getValue().toString() + "%");
        case FilterOperatorRequest.NOT_LIKE:
          return criteriaBuilder.notLike(path.as(String.class), "%" + filter.getValue().toString() + "%");
        case FilterOperatorRequest.ILIKE:
          return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + filter.getValue().toString().toLowerCase() + "%");
        case FilterOperatorRequest.NOT_ILIKE:
          return criteriaBuilder.notLike(criteriaBuilder.lower(path.as(String.class)), "%" + filter.getValue().toString().toLowerCase() + "%");
        case FilterOperatorRequest.IN:
          CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(path);
          ((List<?>) filter.getValue()).forEach(inClause::value);
          return inClause;
        case FilterOperatorRequest.NOT_IN:
          CriteriaBuilder.In<Object> notInClause = criteriaBuilder.in(path);
          ((List<?>) filter.getValue()).forEach(notInClause::value);
          return criteriaBuilder.not(notInClause);
        case FilterOperatorRequest.ALL_IN:
          // This will require some custom logic based on what you define as "ALL IN" in your business logic
          return buildAllInOperator(path, filter.getValue(), criteriaBuilder);
        case FilterOperatorRequest.ANY_IN:
          // This will require some custom logic based on what you define as "ANY IN" in your business logic
          return buildAnyInOperator(path, filter.getValue(), criteriaBuilder);
        default:
          return null;
      }
    };
  }


  private static Predicate buildAllInOperator(Path<Object> path, Object value, CriteriaBuilder criteriaBuilder) {
    // Example logic for ALL IN (you may need to adjust based on your business rules)
    // Here, I'm assuming ALL IN means the value must exist in all of a list (this is a simplified version)
    CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(path);
    ((List<?>) value).forEach(inClause::value);
    return criteriaBuilder.and(inClause); // Example: all items in the list should match
  }

  private static Predicate buildAnyInOperator(Path<Object> path, Object value, CriteriaBuilder criteriaBuilder) {
    // Example logic for ANY IN (you may need to adjust based on your business rules)
    // Here, I'm assuming ANY IN means at least one value in the list matches
    CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(path);
    ((List<?>) value).forEach(inClause::value);
    return criteriaBuilder.or(inClause); // Example: any item in the list should match
  }

}
