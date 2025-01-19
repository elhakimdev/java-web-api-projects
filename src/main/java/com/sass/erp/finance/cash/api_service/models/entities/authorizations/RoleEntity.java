package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship.UserHasRolesEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_ROLES")
public class RoleEntity extends BaseEntity {
  @Column(name = "role_name", nullable = false)
  private String roleName;

  @Column(name = "role_description", nullable = false)
  private String roleDescription;

  @Column(name = "role_guard_name", nullable = false)
  private String roleGuardName;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserHasRolesEntity> userRoles = new HashSet<>();
}
