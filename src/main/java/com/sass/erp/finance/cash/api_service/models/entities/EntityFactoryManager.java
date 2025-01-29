package com.sass.erp.finance.cash.api_service.models.entities;

import com.sass.erp.finance.cash.api_service.annotations.Fillable;
import com.sass.erp.finance.cash.api_service.annotations.Guarded;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class EntityFactoryManager {
  public static <T extends BaseEntity, Payload extends Map<String, Object>> T create(Class<T> clazz, Payload payload) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
    return EntityFactoryManager.create(clazz, payload, null);
  }

  public static <T extends BaseEntity, Payload extends Map<String, Object>> T create(Class<T> clazz, Payload payload, BiConsumer<Payload, T> mapFunc) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
    T entity = clazz.getDeclaredConstructor().newInstance();
    if(mapFunc != null) {
      mapFunc.accept(payload, entity);  // Now mapFunc can modify the entity directly

      // Optionally, after manual mapping, copy the fields from the new entity to the current instance
      // TODO : copyFields(entity);  // Copy the updated fields to the original entity
    } else {
      // Default auto-mapping using reflection
      for (Field field : entity.getClass().getDeclaredFields()) {
        field.setAccessible(true);

        // Skip if the field is guarded
        if (field.isAnnotationPresent(Guarded.class)) {
          continue;
        }

        // If the field is fillable, set its value
        if (field.isAnnotationPresent(Fillable.class)) {
          if (payload.containsKey(field.getName())) {
            field.set(entity, payload.get(field.getName()));
          }
        }
      }
    }


    EntityFactoryManager.prePersistId(entity);
    EntityFactoryManager.prePersistTimestamp(entity);
    EntityFactoryManager.prePersistAuditLog(entity);

    return entity;
  }

  protected static <T extends BaseEntity> void prePersistId(T entity) {
    // TODO Some check need performed to check id and external id was define or not.
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
  }

  protected static <T extends BaseEntity> void prePersistTimestamp(T entity) {
    EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();
    timeStamp.setCreatedAt(LocalDateTime.now());
    timeStamp.setUpdatedAt(LocalDateTime.now());
    entity.setTimeStamp(timeStamp);
  }

  protected static <T extends BaseEntity> void prePersistAuditLog(T entity) {
    EmbeddedAuditLog auditLog = new EmbeddedAuditLog();
    auditLog.setCreatedBy(BaseEntity.DEFAULT_AUDITOR_FLAG);
    entity.setAuditLog(auditLog);
  }
}
