package com.sass.erp.finance.cash.api_service.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_COMMONS_MASTERS_USER_GROUP")
public class UserGroupEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID) // Generates UUID automatically
  private UUID id;
}
