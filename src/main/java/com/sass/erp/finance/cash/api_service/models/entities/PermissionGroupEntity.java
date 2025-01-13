package com.sass.erp.finance.cash.api_service.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_COMMONS_MASTERS_PERMISSION_GROUPS")
public class PermissionGroupEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID) // Generates UUID automatically
  private UUID id;

  @Column(name="name", nullable = false)
  private String name;

  @Column(name="description", nullable = false)
  private String description;

  @Column(name="code", nullable = true)
  private String code;
}
