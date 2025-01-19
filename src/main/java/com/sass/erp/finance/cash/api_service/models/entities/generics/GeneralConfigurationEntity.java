package com.sass.erp.finance.cash.api_service.models.entities.generics;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_GENERIC_GENERAL_CONFIGURATIONS")
public class GeneralConfigurationEntity extends BaseEntity {
    @Column(name = "general_config_version")
    private String configVersion;

    @Column(name = "general_config_key")
    private String configKey;

    @Column(name = "general_config_value")
    private String configValue;

    @Column(name = "general_config_default_value")
    private String configDefaultValue;

    @Column(name = "general_config_type")
    private String configType;

    @Column(name = "general_config_category")
    private String category;

    @Column(name = "general_config_source")
    private String source;

    @Column(name = "general_config_description")
    private String configDescription;

    @Column(name = "general_config_environment")
    private String configEnvironment;

    @Column(name = "general_config_is_active")
    private Boolean isActive;

    @Column(name = "general_config_is_encrypted")
    private Boolean isEncrypted;

    @Column(name = "general_config_is_global")
    private Boolean isGlobal;

    @Column(name = "general_config_is_scoped")
    private Boolean isScoped;

    @Column(name = "general_config_is_mandatory")
    private Boolean isMandatory;

    @Column(name = "general_config_activated_by")
    private String activatedBy;

    @Column(name = "general_config_expiration_date")
    private Date expirationDate;

    @Column(name = "general_config_expiration_time")
    private Time expirationTime;
}
