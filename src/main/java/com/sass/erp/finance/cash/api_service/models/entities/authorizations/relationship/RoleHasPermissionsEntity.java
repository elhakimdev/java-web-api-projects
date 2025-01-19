package com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.PermissionEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CASH_AUTHORIZATION_ASSOC_RELATION_ROLE_HAS_PERMISSIONS")
public class RoleHasPermissionsEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;

    @Column(name = "roles_has_permissions_assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "roles_has_permissions_assigned_by")
    private LocalDateTime assignedBy;

    @Column(name = "roles_has_permissions_unassigned_at")
    private LocalDateTime unassignedAt;

    @Column(name = "roles_has_permissions_unassigned_by")
    private LocalDateTime unassignedBy;
}
