package com.sass.erp.finance.cash.api_service.http.controllers.impl;

import com.sass.erp.finance.cash.api_service.http.controllers.Controller;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ControllerImpl implements Controller {
  @Autowired
  protected HttpServletRequest request;

  protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
