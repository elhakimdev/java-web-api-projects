package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sass.erp.finance.cash.api_service.annotations.Fillable;
import com.sass.erp.finance.cash.api_service.annotations.Guarded;
import com.sass.erp.finance.cash.api_service.annotations.SensitiveInformation;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.relationship.UserHasRolesEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_USERS")
public class UserEntity extends BaseEntity {
  @Fillable
  @Column(name = "username", nullable = false, unique = true)
  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  private String username;

  @Fillable
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Guarded
  @SensitiveInformation
  @Column(name = "password", nullable = false)
  private String password;

  @Fillable
  @Column(name = "is_active")
  private Boolean isActive;

  @Fillable
  @Column(name = "is_locked")
  private Boolean isLocked;

  @Fillable
  @Column(name = "is_verified")
  private Boolean isVerified;

  @Fillable
  @Column(name = "is_external")
  private Boolean isExternal;

  @Fillable
  @Column(name = "is_migrated")
  private Boolean isMigrated;

  @Fillable
  @Column(name = "email_verified_at")
  private LocalDateTime emailVerifiedAt;

  @Column(name = "last_login_at")
  private LocalDateTime lastLoginAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserHasRolesEntity> roles = new HashSet<>();
}
