package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sass.erp.finance.cash.api_service.annotations.Fillable;
import com.sass.erp.finance.cash.api_service.annotations.SensitiveInformation;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship.UserHasRolesEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_USERS")
public class UserEntity extends BaseEntity {
  @Fillable
  @Column(name = "username", nullable = false)
  private String username;

  @Fillable
  @Column(name = "email", nullable = false)
  private String email;

  @Fillable
  @SensitiveInformation
  @Column(name = "password", nullable = false)
  private String password;

  @Fillable
  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_locked")
  private Boolean isLocked;

  @Column(name = "is_verified")
  private Boolean isVerified;

  @Column(name = "is_external")
  private Boolean isExternal;

  @Column(name = "is_migrated")
  private Boolean isMigrated;

  @Column(name = "email_verified_at")
  private LocalDateTime emailVerifiedAt;

  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserHasRolesEntity> roles = new HashSet<>();
}
