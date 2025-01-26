package com.sass.erp.finance.cash.api_service.models.entities.masters;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuardEntity extends BaseEntity {
  @Column(name = "guard_name")
  private String guardName;

  @Column(name = "guard_description")
  private String guardDescription;
}
