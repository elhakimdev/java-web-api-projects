package com.sass.erp.finance.cash.api_service.http.resources;

import java.util.HashMap;

public abstract class Resource {
  public abstract HashMap<String, Object> toResponse();
}
