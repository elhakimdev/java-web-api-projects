package com.sass.erp.finance.cash.api_service.services.impl.resources;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import com.sass.erp.finance.cash.api_service.services.AdvancedSearchService;
import com.sass.erp.finance.cash.api_service.services.impl.RestfullApiServiceImpl;
import com.sass.erp.finance.cash.api_service.services.resources.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResourceServiceImpl<
    U extends UserEntity, ID extends EmbeddedIdentifier
  > extends RestfullApiServiceImpl<
    U, ID
  > implements UserResourceService<
    U, ID
  > {

  @Autowired
  public UserResourceServiceImpl(
    UserRepository<U, ID> userRepository,
    AdvancedSearchService<U, ID> searchService
  ) {
    this.repository = userRepository;
    this.searchService = searchService;
  }
}
