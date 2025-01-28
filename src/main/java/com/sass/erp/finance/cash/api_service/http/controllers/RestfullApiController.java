package com.sass.erp.finance.cash.api_service.http.controllers;

import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import org.springframework.http.HttpEntity;

import java.util.AbstractMap;
import java.util.List;

public interface RestfullApiController<T extends BaseEntity> {
  HttpEntity<RestfullApiResponse<List<AbstractMap<String, Object>>, Object>> index();
}
