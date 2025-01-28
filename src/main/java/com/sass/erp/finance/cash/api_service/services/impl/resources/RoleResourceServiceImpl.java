package com.sass.erp.finance.cash.api_service.services.impl.resources;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.RoleRepository;
import com.sass.erp.finance.cash.api_service.services.AdvancedSearchService;
import com.sass.erp.finance.cash.api_service.services.impl.RestfullApiServiceImpl;
import com.sass.erp.finance.cash.api_service.services.resources.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleResourceServiceImpl<
    R extends RoleEntity, ID extends EmbeddedIdentifier
  > extends RestfullApiServiceImpl<
    R, ID
  > implements RoleResourceService<
    R, ID
  > {

  @Autowired
  public RoleResourceServiceImpl(
    RoleRepository<R, ID> userRepository,
    AdvancedSearchService<R, ID> searchService
  ) {
    this.repository = userRepository;
    this.searchService = searchService;
  }
}
