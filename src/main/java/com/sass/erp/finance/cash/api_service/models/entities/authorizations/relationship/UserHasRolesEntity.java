package com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.RoleEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
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
@Table(name = "CASH_AUTHORIZATION_ASSOC_RELATION_USER_HAS_ROLES")
public class UserHasRolesEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "user_has_roles_assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "user_has_roles_assigned_by")
    private LocalDateTime assignedBy;

    @Column(name = "user_has_roles_unassigned_at")
    private LocalDateTime unassignedAt;

    @Column(name = "user_has_roles_unassigned_by")
    private LocalDateTime unassignedBy;
}
