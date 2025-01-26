package com.sass.erp.finance.cash.api_service.models.entities.configurations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CASH_CONFIG_DATA_TYPE")
public class ConfigDataTypeEntity extends BaseEntity {
    @Column(name = "conf_data_type_kind", nullable = false)
    private String configurationDataTypeKind;
}
