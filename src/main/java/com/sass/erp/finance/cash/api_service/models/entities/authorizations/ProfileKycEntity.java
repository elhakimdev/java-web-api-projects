package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_PROFILE_KYC")
public class ProfileKycEntity extends BaseEntity {
    @Column(name = "profile_kyc_status")
    private String kycStatus;

    @Column(name = "profile_kyc_id_doc_type")
    private String kycIdentityDocumentType;

    @Column(name = "profile_kyc_id_doc_no")
    private Long kycIdentityDocumentNumber;

    @Column(name = "profile_kyc_verified_at")
    private LocalDateTime kycIdentityVerifiedAt;

    @Column(name = "profile_kyc_verified_by")
    private String kycIdentityVerifiedBy;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
