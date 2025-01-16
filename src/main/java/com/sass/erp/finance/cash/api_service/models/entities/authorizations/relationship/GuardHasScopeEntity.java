package com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuardHasScopeEntity extends BaseEntity {
    @Column(name = "guard_has_scope_assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "guard_has_scope_assigned_by")
    private LocalDateTime assignedBy;

    @Column(name = "guard_has_scope_unassigned_at")
    private LocalDateTime unassignedAt;

    @Column(name = "guard_has_scope_unassigned_by")
    private LocalDateTime unassignedBy;
}
