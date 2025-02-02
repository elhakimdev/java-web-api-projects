package com.sass.erp.finance.cash.api_service.http.controllers.impl;

import com.sass.erp.finance.cash.api_service.http.controllers.RestfullApiController;
import com.sass.erp.finance.cash.api_service.http.requests.Request;
import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.http.resources.Resource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
public abstract class RestfullApiControllerImpl<
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

  // Abstract method for defining filterable fields
  protected abstract List<String> getFilterableBy();

  // Abstract method for defining searchable fields
  protected abstract List<String> getSearchableBy();

  // Abstract method for defining sortable fields
  protected abstract List<String> getSortableBy();

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

  @PostMapping("/search")
  @Override
  public HttpEntity<RestfullApiResponse<Map<String, Object>, Object>> search(
    @RequestBody AdvanceSearchRequest request, Pageable pageable
  ) {
    // Simply pass the constraints into the request before processing
    request.setFilterableBy(getFilterableBy());
    request.setSearchableBy(getSearchableBy());
    request.setSortableBy(getSortableBy());

    Page<T> page = this.service.search(request, pageable);

    Map<String, Object> paginatedCollectionsResponse = this.resource.toPaginatedCollectionsResponse(page, this.request);

    RestfullApiResponse<Map<String, Object>, Object> response = RestfullApiResponseFactory.success(paginatedCollectionsResponse, "Search result of " + this.getResourceCollectionsName(), HttpStatus.OK);

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/create")
  @Override
  public HttpEntity<RestfullApiResponse<AbstractMap<String, Object>, Object>> store(
    @RequestBody Request request
  ) {
    return null;
  }
}
