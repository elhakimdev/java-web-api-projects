package com.sass.erp.finance.cash.api_service.models.entities.masters.administrative;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedCoordinate;
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
@Table(name = "CASH_MASTERS_ADM_COUNTRIES")
public class CountryEntity extends BaseEntity {

  @Column(name = "name")
  private String countryName;

  @Column(name = "native_name")
  private String countryNativeName;

  @Column(name = "iso_3")
  private String iso3;

  @Column(name = "iso_2")
  private String iso2;

  @Column(name = "numeric_code")
  private String numericCode;

  @Column(name = "phone_code")
  private String phoneCode;

  @Column(name = "capital")
  private String capital;

  @Column(name = "ccy_code")
  private String currencyCode;

  @Column(name = "ccy_name")
  private String currencyName;

  @Column(name = "ccy_symbol")
  private String currencySymbol;

  @Column(name = "nationality")
  private String nationality;

  @Embedded
  private EmbeddedCoordinate coordinate;
}
