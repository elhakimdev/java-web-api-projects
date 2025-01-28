package com.sass.erp.finance.cash.api_service.http.resources;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public abstract class Resource<T> {
  protected T entity;

  protected String name;

  protected abstract HashMap<String, Object> toCollections();

  protected abstract HashMap<String, Object> toResource();

  public AbstractMap<String, Object> toResponse(T entity) {
    this.setEntity(entity);
    return this.toResource();
  }

  public Map<String, Object> toPaginatedCollectionsResponse(
    Page<T> pageableEntity,
    HttpServletRequest request
  ) {

    List<HashMap<String, Object>> entities = pageableEntity
      .stream()
      .map(data -> {
        this.setEntity(data);
        return this.toCollections();
      }).toList();

    Map<String, Object> paginatedResponse = new HashMap<>();
    String baseUrl = getBaseUrl(request);

    paginatedResponse.put("total", pageableEntity.getTotalElements());
    paginatedResponse.put("totalPage", pageableEntity.getTotalElements() / pageableEntity.getSize());
    paginatedResponse.put("perPage", pageableEntity.getSize());
    paginatedResponse.put("currentPage", pageableEntity.getNumber() + 1);
    paginatedResponse.put("lastPage", pageableEntity.getTotalPages() != 0 ? baseUrl + "?page=" + pageableEntity.getTotalPages() + "&size=" + pageableEntity.getSize() : null);
    paginatedResponse.put("firstPageUrl", baseUrl + "?page=1" + "&size=" + pageableEntity.getSize());
    paginatedResponse.put("nextPageUrl", pageableEntity.hasNext() ? (baseUrl + "?=page=" + pageableEntity.getNumber() + 2) + "&size=" + pageableEntity.getSize() : null);
    paginatedResponse.put("prevPageUrl", pageableEntity.hasPrevious() ? baseUrl + "?page=" + pageableEntity.getNumber() + "&size=" + pageableEntity.getSize() : null);
    paginatedResponse.put("path", baseUrl);
    paginatedResponse.put("from", pageableEntity.getNumber() * pageableEntity.getSize() + 1);
    paginatedResponse.put("to", (pageableEntity.getNumber() + 1) * pageableEntity.getSize());
    paginatedResponse.put("entities", entities);

    return paginatedResponse;
  }

  private static String getBaseUrl(HttpServletRequest request) {
    String scheme = request.getScheme(); // http or https
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();

    StringBuilder baseUrl = new StringBuilder();
    baseUrl.append(scheme).append("://").append(serverName);

    if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
      baseUrl.append(":").append(serverPort);
    }

    baseUrl.append(contextPath).append(request.getServletPath());
    return baseUrl.toString();
  }
}
