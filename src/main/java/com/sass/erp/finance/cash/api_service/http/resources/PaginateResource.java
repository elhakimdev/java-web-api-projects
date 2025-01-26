package com.sass.erp.finance.cash.api_service.http.resources;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaginateResource {
  public static <T extends HashMap<String, Object>, P extends  BaseEntity> Map<String, Object> toPaginateResponse(
      HttpServletRequest request,
      List<T> entities,
      Page<P> page,
      String collectionsName
  ) {
    Map<String, Object> paginatedResponse = new HashMap<>();

    String baseUrl = getBaseUrl(request);

    paginatedResponse.put("total", page.getTotalElements());
    paginatedResponse.put("perPage", page.getSize());
    paginatedResponse.put("currentPage", page.getNumber() + 1);
    paginatedResponse.put("lastPage", baseUrl + "?page=" + page.getTotalPages());
    paginatedResponse.put("firstPageUrl", baseUrl + "?page=1");
    paginatedResponse.put("nextPageUrl", page.hasNext() ? baseUrl + "?=page=" + page.getNumber() + 2 : null);
    paginatedResponse.put("prevPageUrl", page.hasPrevious() ? baseUrl + "?page=" + page.getNumber() : null);
    paginatedResponse.put("path", baseUrl);
    paginatedResponse.put("from", page.getNumber() * page.getSize() + 1);
    paginatedResponse.put("to", (page.getNumber() + 1) * page.getSize());
    paginatedResponse.put(collectionsName, entities);

    return paginatedResponse;
  }


  private static String getBaseUrl(HttpServletRequest request) {
    String scheme = request.getScheme(); // http or https
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();

    StringBuilder baseUrl = new StringBuilder();
    baseUrl.append(scheme).append("://").append(serverName);

    // Only include port if it's not the default port
    if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
      baseUrl.append(":").append(serverPort);
    }

    baseUrl.append(contextPath).append(request.getServletPath());
    return baseUrl.toString();
  }
}
