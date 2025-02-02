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
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "guard_name", nullable = false)
  private String guardName;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserHasRolesEntity> users = new HashSet<>();
}
