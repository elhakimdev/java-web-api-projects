package com.sass.erp.finance.cash.api_service.http.controllers.impl.resources.role;

import com.sass.erp.finance.cash.api_service.http.controllers.impl.RestfullApiControllerImpl;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import java.util.List;

@RestController
@RequestMapping("/api/resources/roles")
public class RoleControllerImpl extends RestfullApiControllerImpl<RoleEntity, EmbeddedIdentifier> {
  public String getResourceName() {
    return "role";
  }

  /**
   * @return
   */
  @Override
  protected List<String> getFilterableBy() {
    return List.of();
  }

  /**
   * @return
   */
  @Override
  protected List<String> getSearchableBy() {
    return List.of();
  }

  /**
   * @return
   */
  @Override
  protected List<String> getSortableBy() {
    return List.of();
  }
}
