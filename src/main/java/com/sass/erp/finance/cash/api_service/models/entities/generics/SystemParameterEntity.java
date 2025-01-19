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
@Table(name = "CASH_GENERIC_SYSTEM_PARAMETERS")
public class SystemParameterEntity extends BaseEntity {
    @Column(name = "system_parameters_key")
    private String paramKey;

    @Column(name = "system_parameters_value")
    private String paramValue;

    @Column(name = "system_parameters_type")
    private String paramType;

    @Column(name = "system_parameters_category")
    private String paramCategory;

    @Column(name = "system_parameters_source")
    private String source;

    @Column(name = "system_parameters_environment")
    private String configEnvironment;

    @Column(name = "system_parameters_description")
    private String paramDescription;

    @Column(name = "system_parameters_is_active")
    private String isActive;

    @Column(name = "system_parameters_is_encrypted")
    private String isEncrypted;

    @Column(name = "system_parameters_is_global")
    private String isGlobal;

    @Column(name = "system_parameters_is_scoped")
    private String isScoped;

    @Column(name = "system_parameters_is_mandatory")
    private Boolean isMandatory;

    @Column(name = "system_parameters_activated_by")
    private String activatedBy;

    @Column(name = "system_parameters_expiration_date")
    private Date expirationDate;

    @Column(name = "system_parameters_expiration_time")
    private Time expirationTime;
}
