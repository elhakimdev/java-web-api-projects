package com.sass.erp.finance.cash.api_service.models.entities.masters;

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
@NoArgsConstructor
@Table(name = "CASH_MASTERS_SCOPE_CATEGORY")
public class ScopeCategoryEntity extends BaseEntity {
    @Column(name = "scope_category_name", nullable = false)
    private String name;
}
