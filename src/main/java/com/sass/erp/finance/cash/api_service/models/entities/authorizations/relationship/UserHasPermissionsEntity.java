package com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.PermissionEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import jakarta.persistence.*;
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
@Table(name = "CASH_AUTHORIZATION_ASSOC_RELATION_USER_HAS_PERMISSIONS")
public class UserHasPermissionsEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;

    @Column(name = "user_has_permissions_assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "user_has_permissions_assigned_by")
    private LocalDateTime assignedBy;

    @Column(name = "user_has_permissions_unassigned_at")
    private LocalDateTime unassignedAt;

    @Column(name = "user_has_permissions_unassigned_by")
    private LocalDateTime unassignedBy;
}
