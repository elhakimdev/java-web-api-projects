package com.sass.erp.finance.cash.api_service.models.entities.masters.administrative;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_MASTERS_ADM_SUB_REGIONS")
public class SubRegionEntity extends BaseEntity {
  @Column(name = "name")
  private String regionName;

  @Column(name =   "wiki_data_id")
  private String wikiDataId;
}
