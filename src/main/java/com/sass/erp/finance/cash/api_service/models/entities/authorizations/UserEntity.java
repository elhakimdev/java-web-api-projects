package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
  @Column(name = "user_username", nullable = false)
  private String userUsername;

  @Column(name = "user_email", nullable = false)
  private String userEmail;

  @Column(name = "user_password", nullable = false)
  private String userPassword;

  @Column(name = "user_is_active", nullable = false)
  private Boolean userIsActive;

  @Column(name = "email_verified_at")
  private LocalDateTime userEmailVerifiedAt;

  @Column(name = "last_login_at")
  private LocalDateTime userLastLoginAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserHasRolesEntity> userRoles = new HashSet<>();
}
