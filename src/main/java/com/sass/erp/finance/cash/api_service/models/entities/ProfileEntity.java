package com.sass.erp.finance.cash.api_service.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CASH_COMMONS_MASTERS_PROFILES")
public class ProfileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generates UUID automatically
    private UUID id;
}
