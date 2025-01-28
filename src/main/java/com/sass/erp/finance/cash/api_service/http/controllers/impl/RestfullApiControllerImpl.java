package com.sass.erp.finance.cash.api_service.http.controllers.impl;

import com.sass.erp.finance.cash.api_service.http.controllers.RestfullApiController;
import com.sass.erp.finance.cash.api_service.http.resources.Resource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

public class RestfullApiControllerImpl<
  T extends BaseEntity,
  ID extends EmbeddedIdentifier
> extends ControllerImpl implements RestfullApiController<T> {
  @Autowired
  protected RestfullApiService<T, ID> service;

  @Autowired
  protected Resource<T> resource;

  public String getResourceName() {
    return "data";
  }

  public String getResourceCollectionsName() {
    return this.getResourceName() + "'s";
  }

  @GetMapping
  @Override
  public HttpEntity<RestfullApiResponse<Map<String, Object>, Object>> index(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size
  ) {

    Page<T> pageable = this.service.findAll(PageRequest.of(page, size));

    Map<String, Object> paginatedCollectionsResponse = this.resource.toPaginatedCollectionsResponse(pageable, this.request);

    RestfullApiResponse<Map<String, Object>, Object> response = RestfullApiResponseFactory.success(paginatedCollectionsResponse, "List " + this.getResourceCollectionsName(), HttpStatus.OK);

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/{uuid}")
  @Override
  public HttpEntity<RestfullApiResponse<AbstractMap<String, Object>, Object>> show(
    @PathVariable String uuid
  ) {
    EmbeddedIdentifier embeddedIdentifier = new EmbeddedIdentifier(UUID.fromString(uuid));
    T byIdentifier = this.service.findByIdentifier(embeddedIdentifier);

    RestfullApiResponse<AbstractMap<String, Object>, Object> response = RestfullApiResponseFactory.success(this.resource.toResponse(byIdentifier), "Retrieve " + this.getResourceCollectionsName(), HttpStatus.OK);

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
