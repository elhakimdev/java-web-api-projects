package com.sass.erp.finance.cash.api_service.models.entities.masters;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "CASH_MASTERS_SCOPE_CATEGORY")
public class ScopeEntity extends BaseEntity {
    @Column(name = "scope_name")
    private String scopeName;

    @Column(name = "scope_description")
    private String scopeDescription;
}
