package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_PROFILES")
public class ProfileEntity extends BaseEntity {
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private ProfileKycEntity profileKyc;
}
