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
public class TimeZoneEntity extends BaseEntity {
  @Column(name = "zone_name")
  private String zoneName;

  @Column(name = "gmt_offset")
  private Integer gmtOffset;

  @Column(name = "gmt_offset_name")
  private String gmtOffsetName;

  @Column(name = "abbreviation")
  private String abbreviation;

  @Column(name = "time_zone_name")
  private String tzName;
}
