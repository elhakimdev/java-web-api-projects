package com.sass.erp.finance.cash.api_service.models.entities;

import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    public static final String DEFAULT_AUDITOR_FLAG = "System";

    @EmbeddedId
    private EmbeddedIdentifier identifier;

    @Embedded
    private EmbeddedExternalID externalIdentifier;

    @Embedded
    private EmbeddedTimeStamp timeStamp;

    @Embedded
    private EmbeddedAuditLog auditLog;

    @PostPersist
    public void postPersist(){
        if(this.getTimeStamp() == null){
            postPersistTimeStamp();
        }

        if(this.getAuditLog() == null){
            postPersistAuditLog();
        }
    }

    @PostUpdate
    public void postUpdate(){
        postUpdateTimeStamp();
        postUpdateAuditLog();
    }

    protected void postPersistTimeStamp() {
        EmbeddedTimeStamp timeStampObj = new EmbeddedTimeStamp();

        if(getTimeStamp().getCreatedAt() == null) {
            timeStampObj.setCreatedAt(LocalDateTime.now());
        }

        if(getTimeStamp().getUpdatedAt() == null) {
            timeStampObj.setUpdatedAt(LocalDateTime.now());
        }

        this.setTimeStamp(timeStampObj);
    }
    protected void postPersistAuditLog() {
        EmbeddedAuditLog auditLogObj = new EmbeddedAuditLog();
        auditLogObj.setCreatedBy(BaseEntity.DEFAULT_AUDITOR_FLAG);
        this.setAuditLog(auditLogObj);
    }

    protected void postUpdateTimeStamp() {
        this.getTimeStamp().setUpdatedAt(LocalDateTime.now());
    }

    protected void postUpdateAuditLog() {
        String lastUpdatedBy = this.getAuditLog().getLastUpdatedBy();
        if (lastUpdatedBy == null) {
            lastUpdatedBy = (this.getAuditLog().getLastDeletedBy() != null) ? this.getAuditLog().getLastDeletedBy() : BaseEntity.DEFAULT_AUDITOR_FLAG;
        }
        this.getAuditLog().setLastUpdatedBy(lastUpdatedBy);
    }
}
