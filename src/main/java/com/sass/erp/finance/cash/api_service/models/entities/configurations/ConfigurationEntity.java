package com.sass.erp.finance.cash.api_service.models.entities.configurations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class ConfigurationEntity extends BaseEntity {
    @Column(name = "conf_name")
    private String name;

    @Column(name = "conf_desc")
    private String desc;

    @Column(name = "conf_param")
    private String parameter;

    @Column(name = "conf_token")
    private String token;

    @Column(name = "conf_perf_impact")
    private String performanceImpact;

    @Column(name = "conf_default_value")
    private String defaultValue;

    @Column(name = "conf_add_info")
    private String additionalValue;

    @Column(name = "conf_multi_var_separator")
    private String multiValSeparator;

    @Column(name = "conf_is_auto")
    private Boolean isAuto = Boolean.FALSE;

    @Column(name = "conf_is_builtin")
    private Boolean isBuiltIn = Boolean.FALSE;

    @Column(name = "conf_is_external")
    private Boolean isExternal = Boolean.FALSE;

    @Column(name = "conf_is_cacheable")
    private Boolean isCacheable = Boolean.FALSE;

    @Column(name = "conf_is_encrypted")
    private Boolean isEncrypted = Boolean.FALSE;

    @Column(name = "conf_is_secret")
    private Boolean isSecret = Boolean.FALSE;

    @Column(name = "conf_is_global")
    private Boolean isGlobal = Boolean.FALSE;

    @Column(name = "conf_is_scoped")
    private Boolean isScoped = Boolean.FALSE;

    @Column(name = "conf_is_scoped")
    private Boolean isEnabled = Boolean.FALSE;

    @Column(name = "conf_is_single_value")
    private Boolean isSingleValue = Boolean.FALSE;
}
