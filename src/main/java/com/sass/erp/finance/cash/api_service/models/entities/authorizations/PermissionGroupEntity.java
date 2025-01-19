package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_PERMISSION_GROUPS")
public class PermissionGroupEntity extends BaseEntity {
  @Column(name="permission_group_description", nullable = false)
  private String permissionGroupName;

  @Column(name="permission_group_name", nullable = false)
  private String permissionGroupDescription;
}
