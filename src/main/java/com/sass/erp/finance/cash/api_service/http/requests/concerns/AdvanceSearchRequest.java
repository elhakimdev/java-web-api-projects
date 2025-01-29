package com.sass.erp.finance.cash.api_service.http.requests.concerns;

import lombok.Data;
import java.util.List;


@Data
public class AdvanceSearchRequest {
  private List<Scope> scopes;
  private List<Filter> filters;
  private Search search;
  private List<Sort> sort;
  private List<Aggregate> aggregates;
  private List<Include> includes;
  private List<String> alwaysIncludes;

  private List<String> filterableBy;
  private List<String> searchableBy;
  private List<String> sortableBy;

  @Data
  public static class Scope {
    private String name;
    private List<String> parameters;
  }

  @Data
  public static class Filter {
    private String type; // "or" or "and"
    private String field;
    private FilterOperatorRequest operator; // Use the enum here
    private Object value;
  }

  @Data
  public static class Search {
    private String value;
    private Boolean caseSensitive;
  }

  @Data
  public static class Sort {
    private String field;
    private SortDirectionRequest direction;
  }

  @Data
  public static class Aggregate {
    private String relation;
    private String type; // e.g., "count", "sum"
    private List<Filter> filters;
  }

  @Data
  public static class Include {
    private String relation;
    private List<Filter> filters;
  }
}
