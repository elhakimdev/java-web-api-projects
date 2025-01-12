package com.sass.erp.finance.cash.api_service.models.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_COMMONS_MASTERS_ROLES")
public class RoleEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID) // Generates UUID automatically
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;
}
