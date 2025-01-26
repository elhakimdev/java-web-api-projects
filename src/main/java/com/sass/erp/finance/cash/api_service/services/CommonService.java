package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Service
public abstract class CommonService<T extends BaseEntity, ID> {
  protected abstract BaseRepository<T, ID> getRepository();

  protected T prePersistAuditLog(@NotNull T entity) {
    EmbeddedAuditLog auditLog = new EmbeddedAuditLog();
    EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();

    timeStamp.setCreatedAt(LocalDateTime.now());
    timeStamp.setUpdatedAt(LocalDateTime.now());
    timeStamp.setRestoredAt(null);
    timeStamp.setDeletedAt(null);

    auditLog.setCreatedBy("SYSTEM_SERVICE");
    auditLog.setLastUpdatedBy("SYSTEM_SERVICE");
    auditLog.setLastRestoredBy(null);
    auditLog.setLastDeletedBy(null);

    entity.setAuditLog(auditLog);
    entity.setTimeStamp(timeStamp);
    return entity;
  }

  protected T prePersistIdentifier(@NotNull T entity) {

    EmbeddedIdentifier embeddedIdentifier = new EmbeddedIdentifier();
    EmbeddedExternalID embeddedExternalID = new EmbeddedExternalID();

    UUID uuid = UUID.randomUUID();
    embeddedIdentifier.setUuid(uuid);

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String generatedCode = now.format(formatter) + System.currentTimeMillis();

    embeddedExternalID.setExternalId("EXT-REF-ID" + generatedCode);
    embeddedExternalID.setSystemRefId("SYS-REF-ID" + generatedCode);
    embeddedExternalID.setUniqueId("UID-REF-ID" + generatedCode);
    embeddedExternalID.setDisplayId("DIS-REF-ID" + generatedCode);

    entity.setIdentifier(embeddedIdentifier);
    entity.setExternalIdentifier(embeddedExternalID);

    return entity;
  }

  protected T preUpdateAuditLog(@NotNull T entity) {
    EmbeddedAuditLog auditLog = new EmbeddedAuditLog();
    EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();

    timeStamp.setUpdatedAt(LocalDateTime.now());
    entity.setTimeStamp(timeStamp);

    auditLog.setLastUpdatedBy("SYSTEM_SERVICE");
    entity.setAuditLog(auditLog);

    return entity;
  }

  public List<T> index() {
    return this.getRepository().findAll();
  }

  public Page<T> index(int page, int size) {
    PageRequest pageable = PageRequest.of(page, size);
    return this.getRepository().findAll(pageable);
  }

  public T create(T entity){
    return this.getRepository().save(entity);
  }
}
