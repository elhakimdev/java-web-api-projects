package com.sass.erp.finance.cash.api_service.http.controllers;

import com.sass.erp.finance.cash.api_service.http.requests.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface RestfullApiController<T extends BaseEntity> {
  HttpEntity<RestfullApiResponse<Map<String, Object>, Object>> search(AdvanceSearchRequest request, Pageable pageable);
  HttpEntity<RestfullApiResponse<Map<String, Object>, Object>> index(int page, int size);
  HttpEntity<RestfullApiResponse<AbstractMap<String, Object>, Object>> show(String uuid);
}
