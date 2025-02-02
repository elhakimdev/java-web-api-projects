package com.sass.erp.finance.cash.api_service.models.specifications;

import com.sass.erp.finance.cash.api_service.annotations.SensitiveInformation;
import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Advance search spec
 */
public class AdvanceSearchSpecification {
  public static <T> Specification<T> withSearch(AdvanceSearchRequest searchRequest) {
    return (root, query, criteriaBuilder) -> {
        Field[] fields = root.getJavaType().getDeclaredFields();

        // Filter for String fields only (since we assume we're searching text fields) exclude field contains sensitive data.
        List<String> searchableFields = Arrays.stream(fields)
          .filter(field -> String.class.isAssignableFrom(field.getType()) && !field.isAnnotationPresent(SensitiveInformation.class))
          .map(Field::getName)
          .toList();

        // If no fields are found, fallback to default fields
        if (searchableFields.isEmpty()) {
          searchableFields = List.of("name", "description"); // Default fields
        }

        // Build dynamic search conditions for each field
        Predicate[] predicates = searchableFields.stream()
          .filter(field -> isSearchable(searchRequest, field)) // Only include fields that are searchable
          .map(field -> {
            Path<String> path = root.get(field);  // Access field dynamically
            return criteriaBuilder.like(criteriaBuilder.lower(path), "%" + searchRequest.getSearch().getValue().toLowerCase() + "%");
          })
          .toArray(Predicate[]::new);

        // Combine all predicates using 'or' logic
        return criteriaBuilder.or(predicates);
    };
  }

  private static Boolean isSearchable(AdvanceSearchRequest searchRequest, String field) {
    return searchRequest.getFilterableBy() == null || searchRequest.getFilterableBy().isEmpty() || searchRequest.getFilterableBy().contains(field);
  }
}
