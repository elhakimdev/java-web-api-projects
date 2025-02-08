package com.sass.erp.finance.cash.api_service.http.controllers.impl.resources.user;
import com.sass.erp.finance.cash.api_service.http.controllers.impl.RestfullApiControllerImpl;
import com.sass.erp.finance.cash.api_service.http.requests.impl.UserRequestImpl;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resources/users")
public class UserControllerImpl extends RestfullApiControllerImpl<UserEntity, EmbeddedIdentifier> {
  public String getResourceName() {
    return "user";
  }

  @Override
  protected List<String> getFilterableBy() {
    return List.of();
  }

  @Override
  protected List<String> getSearchableBy() {
    return List.of("email", "name");
  }

  @Override
  protected List<String> getSortableBy() {
    return List.of("email", "name");
  }
  
  @Override
  protected Class<UserEntity> getEntityClass() {
    return UserEntity.class;
  }

  @Override
  protected Class<UserRequestImpl> getRequestClass() {
    return UserRequestImpl.class;
  }

  @Override
  protected UserEntity beforeStore(UserEntity entity) {
    String originalPassword = entity.getPassword();
    String encodedPassword = this.encoder.encode(originalPassword);
    entity.setPassword(encodedPassword);
    return entity;
  }
}