package com.sass.erp.finance.cash.api_service.http.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sass.erp.finance.cash.api_service.http.controllers.RestfullApiController;
import com.sass.erp.finance.cash.api_service.http.requests.Request;
import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.http.resources.Resource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.EntityFactoryManager;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
public abstract class RestfullApiControllerImpl<
  T extends BaseEntity,
  ID extends EmbeddedIdentifier
  > extends ControllerImpl implements RestfullApiController<T> {

  @Autowired
  protected RestfullApiService<T, ID> service;

  @Autowired
  protected Resource<T> resource;

  @Autowired
  protected BCryptPasswordEncoder encoder;

  @Autowired
  protected ModelMapper mapper;

  @Autowired
  protected ObjectMapper objectMapper;

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

  // Abstract method for defining entity class
  protected abstract Class<T> getEntityClass();

  protected abstract Class<?> getRequestClass();

  // Hook will be fired before saved entity persisted into db;
  protected T beforeStore(T entity) {
    return entity;
  }

  // Hook will be fired after saved entity persisted into db;
  protected T afterStore(T entity) {
    return entity;
  }

  // Hook will be fired before updated entity persisted into db;
  protected T beforeUpdate(T entity) {
    return entity;
  }

  // Hook will be fired after updated entity persisted into db;
  protected T afterUpdate(T entity) {
    return entity;
  }

  @GetMapping
  @Override
  public HttpEntity<RestfullApiResponse<Map<String, Object>>> index(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size
  ) {

    // Find all entity with or without pagination constrains;
    // Default pagination used if no provided;
    Page<T> pageable = this.service.findAll(PageRequest.of(page - 1, size));

    // Transform to json response as a resource collections.
    Map<String, Object> paginatedCollectionsResponse = this.resource.toPaginatedCollectionsResponse(pageable, this.request);

    // Transform to json response as a resources.
    RestfullApiResponse<Map<String, Object>> response = RestfullApiResponseFactory.success(paginatedCollectionsResponse, "List " + this.getResourceCollectionsName(), HttpStatus.OK);

    // Return HTTP Response as json.
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/{uuid}")
  @Override
  public HttpEntity<RestfullApiResponse<AbstractMap<String, Object>>> show(
    @PathVariable String uuid
  ) {
    // Create and embedded ID instance;
    EmbeddedIdentifier embeddedIdentifier = new EmbeddedIdentifier(UUID.fromString(uuid));

    // Find entity by given embedded instance;
    T byIdentifier = this.service.findByIdentifier(embeddedIdentifier);

    // Transform to json response as a resource;
    RestfullApiResponse<AbstractMap<String, Object>> response = RestfullApiResponseFactory.success(this.resource.toResponse(byIdentifier), "Retrieve " + this.getResourceName(), HttpStatus.OK);

    // Return HTTP Response as json.
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/search")
  @Override
  public HttpEntity<RestfullApiResponse<Map<String, Object>>> search(
    @RequestBody AdvanceSearchRequest request, Pageable pageable
  ) {
    // Simply pass the constraints into the request before processing
    request.setFilterableBy(getFilterableBy());
    request.setSearchableBy(getSearchableBy());
    request.setSortableBy(getSortableBy());

    // Search entities based given constraints;
    Page<T> page = this.service.search(request, pageable);

    // Transform query result as a paginated collection response;
    Map<String, Object> paginatedCollectionsResponse = this.resource.toPaginatedCollectionsResponse(page, this.request);

    // Transform to json response as a resource collections.
    RestfullApiResponse<Map<String, Object>> response = RestfullApiResponseFactory.success(paginatedCollectionsResponse, "Search result of " + this.getResourceCollectionsName(), HttpStatus.OK);

    // Return HTTP Response as json.
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/create")
  @Override
  public HttpEntity<RestfullApiResponse<AbstractMap<String, Object>>> store(
    @RequestBody Object request
  ) throws IllegalArgumentException, HttpMessageNotReadableException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

    logger.info("{}", request);

    Request req  = (Request) this.objectMapper.convertValue(request, getRequestClass());

    logger.info("Request: {}", req);

    // Create entity using factory manager to automatically map request into entity;
    T entity = EntityFactoryManager.create(getEntityClass(), req);

    // Run before save entity hooks;
    T beforeStore = this.beforeStore(entity);

    T saved = this.service.save(beforeStore);

    // Run after save entity hooks;
    this.beforeStore(entity);
    // Transform to json response as a resources;
    RestfullApiResponse<AbstractMap<String, Object>> response = RestfullApiResponseFactory.success(this.resource.toResponse(saved), "Success saving new " + this.getResourceName(), HttpStatus.OK);

    // Return HTTP Response as json.
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
