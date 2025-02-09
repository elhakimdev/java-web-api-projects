package com.sass.erp.finance.cash.api_service.http.controllers;

import com.sass.erp.finance.cash.api_service.http.requests.Request;
import com.sass.erp.finance.cash.api_service.http.requests.impl.RequestImpl;
import com.sass.erp.finance.cash.api_service.http.requests.impl.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import oracle.jdbc.OracleDatabaseException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Map;

public interface RestfullApiController<T extends BaseEntity> {
  HttpEntity<RestfullApiResponse<Map<String, Object>>> search(AdvanceSearchRequest request, Pageable pageable);
  HttpEntity<RestfullApiResponse<Map<String, Object>>> index(int page, int size);
  HttpEntity<RestfullApiResponse<AbstractMap<String, Object>>> show(String uuid);
  HttpEntity<RestfullApiResponse<AbstractMap<String, Object>>> store(Object request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, IOException, OracleDatabaseException;
  HttpEntity<RestfullApiResponse<AbstractMap<String, Object>>> update(Object request, String uuid) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException;
}
