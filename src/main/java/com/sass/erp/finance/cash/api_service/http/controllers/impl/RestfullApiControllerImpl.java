package com.sass.erp.finance.cash.api_service.http.controllers.impl;

import com.sass.erp.finance.cash.api_service.http.controllers.RestfullApiController;
import com.sass.erp.finance.cash.api_service.http.resources.Resource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

public class RestfullApiControllerImpl<
  T extends BaseEntity,
  ID extends EmbeddedIdentifier
> extends ControllerImpl implements RestfullApiController<T> {
  @Autowired
  protected RestfullApiService<T, ID> service;

  @Autowired
  protected Resource<T> resource;


  // Internal usage only from override in child class

  public String getResourceName() {
    return "data";
  }

  public String getResourceCollectionsName() {
    return this.getResourceName() + "'s";
  }

  // Crud Method
  @GetMapping
  @Override
  public HttpEntity<RestfullApiResponse<List<AbstractMap<String, Object>>, Object>> index() {

    Stream<AbstractMap<String, Object>> resultStream = this.service.findAll().stream().map((data) -> {
      this.resource.setEntity(data);
      return this.resource.toResponse(data);
    });

    RestfullApiResponse<List<AbstractMap<String, Object>>, Object> response = RestfullApiResponseFactory.success(resultStream.toList(), "List " + this.getResourceCollectionsName(), HttpStatus.OK);

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
