package com.sass.erp.finance.cash.api_service.models.specifications;

import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdvanceSortSpecification {
  public static <T> Specification<T> withSort(AdvanceSearchRequest searchRequest) {
    return ((root, query, criteriaBuilder) -> {

      // Assert non null query
      assert query != null;

      // Create a list to accumulate the order by clauses
      List<Order> orders = new ArrayList<>();

      if (searchRequest.getSort() != null) {
        for (AdvanceSearchRequest.Sort sort : searchRequest.getSort()) {
          if (isSortable(searchRequest, sort.getField())) {
            // Add the first sorting condition
            orders.add(
              "ASCENDING".equalsIgnoreCase(String.valueOf(sort.getDirection()))
                ? criteriaBuilder.asc(root.get(sort.getField()))
                : criteriaBuilder.desc(root.get(sort.getField()))
            );
          }
        }
      }

      query.orderBy(orders);

      return  criteriaBuilder.conjunction();
    });
  }

  // Checks if a field is sortable
  private static boolean isSortable(AdvanceSearchRequest searchRequest, String field) {
    // If sortableBy is empty or null, assume all fields are sortable
    return searchRequest.getSortableBy() == null || searchRequest.getSortableBy().isEmpty() || searchRequest.getSortableBy().contains(field);
  }
}
