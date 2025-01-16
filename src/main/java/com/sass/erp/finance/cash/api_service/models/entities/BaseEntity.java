package com.sass.erp.finance.cash.api_service.models.entities;

import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
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
    private EmbeddedUUID id;

    @Embedded
    private EmbeddedTimeStamp timestamp;

    @Embedded
    private EmbeddedAuditLog auditLog;

    @PostPersist
    public void postPersist(){
        if(this.getTimestamp() == null){
            this.postPersistTimeStamp();
        }

        if(this.getAuditLog() == null){
            this.postPersistAuditLog();
        }
    }

    @PostUpdate
    public void postUpdate(){
        this.postUpdateTimeStamp();
        this.postUpdateAuditLog();
    }

    protected void postPersistTimeStamp() {
        EmbeddedTimeStamp timeStampObj = new EmbeddedTimeStamp();

        if(this.getTimestamp().getCreatedAt() == null) {
            timeStampObj.setCreatedAt(LocalDateTime.now());
        }

        if(this.getTimestamp().getUpdatedAt() == null) {
            timeStampObj.setUpdatedAt(LocalDateTime.now());
        }

        this.setTimestamp(timeStampObj);
    }
    protected void postPersistAuditLog() {
        EmbeddedAuditLog auditLogObj = new EmbeddedAuditLog();
        auditLogObj.setCreatedBy(BaseEntity.DEFAULT_AUDITOR_FLAG);
        this.setAuditLog(auditLogObj);
    }

    protected void postUpdateTimeStamp() {
        this.getTimestamp().setUpdatedAt(LocalDateTime.now());
    }

    protected void postUpdateAuditLog() {
        String lastUpdatedBy = this.getAuditLog().getLastUpdatedBy();
        if (lastUpdatedBy == null) {
            lastUpdatedBy = (this.getAuditLog().getLastDeletedBy() != null) ? this.getAuditLog().getLastDeletedBy() : BaseEntity.DEFAULT_AUDITOR_FLAG;
        }
        this.getAuditLog().setLastUpdatedBy(lastUpdatedBy);
    }
}
