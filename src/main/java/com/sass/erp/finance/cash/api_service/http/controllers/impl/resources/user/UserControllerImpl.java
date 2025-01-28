package com.sass.erp.finance.cash.api_service.http.controllers.impl.resources.user;
import com.sass.erp.finance.cash.api_service.http.controllers.impl.RestfullApiControllerImpl;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources/users")
public class UserControllerImpl extends RestfullApiControllerImpl<UserEntity, EmbeddedIdentifier> {
  public String getResourceName() {
    return "user";
  }
}