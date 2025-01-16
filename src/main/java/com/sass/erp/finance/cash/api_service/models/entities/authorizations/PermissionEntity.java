package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "CASH_AUTHORIZATION_PERMISSIONS")
public class PermissionEntity extends BaseEntity {
  @Column(name="permission_name", nullable = false)
  private String permissionName;

  @Column(name="permission_description", nullable = false)
  private String permissionDescription;

  @Column(name="permission_guard_name", nullable = false)
  private String permissionGuard;
}
