package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class EmbeddedAuditLog {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_deleted_by")
    private String lastDeletedBy;

    @Column(name = "last_restored_by")
    private String lastRestoredBy;
}
