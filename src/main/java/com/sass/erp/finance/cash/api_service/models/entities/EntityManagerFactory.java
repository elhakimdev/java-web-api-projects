package com.sass.erp.finance.cash.api_service.models.entities;

import com.sass.erp.finance.cash.api_service.annotations.Fillable;
import com.sass.erp.finance.cash.api_service.annotations.Guarded;
import com.sass.erp.finance.cash.api_service.http.requests.Request;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@Slf4j
public class EntityManagerFactory {

  public static <T extends BaseEntity, Req extends Request> T create(Class<T> clazz, Req request) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
    return com.sass.erp.finance.cash.api_service.models.entities.EntityManagerFactory.create(clazz, request, null);
  }

  public static <T extends BaseEntity, Req extends Request> T create(Class<T> clazz, Req request, BiConsumer<Req, T> mapFunc) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
    T entity = clazz.getDeclaredConstructor().newInstance();
    return process(EntityManagerStrategy.EntityOperation.CREATE, entity, request, mapFunc);
  }

  public static <T extends BaseEntity, Req extends Request> T update(T entity, Req request) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
    return com.sass.erp.finance.cash.api_service.models.entities.EntityManagerFactory.update(entity, request, null);
  }

  public static <T extends BaseEntity, Req extends Request> T update(T entity, Req request, BiConsumer<Req, T> mapFunc) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
    return process(EntityManagerStrategy.EntityOperation.UPDATE, entity, request, mapFunc);
  }

  private static <T extends BaseEntity, Req extends Request, Operation extends EntityManagerStrategy.EntityOperation> void fillAttributes(T entity, Req request, Operation operation) {

    // Default auto-mapping using reflection
    for (Field field : entity.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      boolean isGuarded = field.isAnnotationPresent(Guarded.class);
      boolean isFillable = field.isAnnotationPresent(Fillable.class);
      boolean isCreating =  operation == EntityManagerStrategy.EntityOperation.CREATE;
      boolean isUpdating =  operation == EntityManagerStrategy.EntityOperation.UPDATE;


      // Skip if the field is guarded and in update action, prevent fill.
      if (isGuarded && isUpdating) {
        log.info("Skipping Process - Entity: {}, field: {}, operation: {}", entity.getClass().getName(), field.getName(), operation.name());
        continue;
      }

      // If is fillable or (field is marked as guarded but in create action) allow to fill.
      if (isFillable || (isGuarded && isCreating)) {

        log.info("Preparing Process - Entity: {}, field: {}, operation: {}", entity.getClass().getName(), field.getName(), operation.name());

        Field[] requestFields = request.getClass().getDeclaredFields();

        // Stream iterate over request field and set into entity
        Arrays.stream(requestFields).forEach((reqField) -> {
          reqField.setAccessible(true);
          if (reqField.getName().equals(field.getName())) {
            try {
              Object value = reqField.get(request);
              EntityManagerStrategy.FieldContext context = new EntityManagerStrategy.FieldContext(field, entity);
              EntityManagerStrategy.applyOperation(operation, context, value);
            } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
            }
          }
        });
      }
    }
  }

  private static <T extends BaseEntity, Req extends Request, Operation extends EntityManagerStrategy.EntityOperation> T process(Operation operation, T entity, Req request, BiConsumer<Req, T> mapFunc){

    if (mapFunc != null) {
      mapFunc.accept(request, entity);
    } else {
      fillAttributes(entity, request, operation);
    }

    if(operation == EntityManagerStrategy.EntityOperation.CREATE) {
      prePersistCommonFields(entity);
    }

    if(operation == EntityManagerStrategy.EntityOperation.UPDATE) {
      preUpdatePersistCommonFields(entity);
    }

    return entity;
  }

  /** Handles all pre-persistence logic (ID, timestamps, audit logs) */
  private static <T extends BaseEntity> void prePersistCommonFields(T entity) {
    prePersistId(entity);
    prePersistTimestamp(entity);
    prePersistAuditLog(entity);
  }

  /** Handles all pre-persistence logic (ID, timestamps, audit logs) */
  private static <T extends BaseEntity> void preUpdatePersistCommonFields(T entity) {
    prePersistUpdateTimestamp(entity);
    prePersistUpdateAuditLog(entity);
  }

  protected static <T extends BaseEntity> void prePersistId(T entity) {
    if (entity == null) {
      throw new IllegalArgumentException("Entity cannot be null");
    }

    // Check if identifier is already set
    if (entity.getIdentifier() == null || entity.getIdentifier().getUuid() == null) {
      EmbeddedIdentifier embeddedIdentifier = new EmbeddedIdentifier();
      embeddedIdentifier.setUuid(UUID.randomUUID());
      entity.setIdentifier(embeddedIdentifier);
    }

    // Check if external identifier is already set
    if (entity.getExternalIdentifier() == null ||
      entity.getExternalIdentifier().getExternalId() == null ||
      entity.getExternalIdentifier().getSystemRefId() == null ||
      entity.getExternalIdentifier().getUniqueId() == null ||
      entity.getExternalIdentifier().getDisplayId() == null) {

      EmbeddedExternalID embeddedExternalID = new EmbeddedExternalID();
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
      String generatedCode = now.format(formatter) + System.currentTimeMillis();

      // Set external id props
      embeddedExternalID.setExternalId("EXT-REF-ID" + generatedCode);
      embeddedExternalID.setSystemRefId("SYS-REF-ID" + generatedCode);
      embeddedExternalID.setUniqueId("UID-REF-ID" + generatedCode);
      embeddedExternalID.setDisplayId("DIS-REF-ID" + generatedCode);

      // Assign external identifier to current entity
      entity.setExternalIdentifier(embeddedExternalID);
    }
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

  protected static <T extends BaseEntity> void prePersistUpdateTimestamp(T entity) {
    EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();
    timeStamp.setUpdatedAt(LocalDateTime.now());
    entity.setTimeStamp(timeStamp);
  }

  protected static <T extends BaseEntity> void prePersistUpdateAuditLog(T entity) {
    EmbeddedAuditLog auditLog = new EmbeddedAuditLog();
    auditLog.setLastUpdatedBy(BaseEntity.DEFAULT_AUDITOR_FLAG);
    entity.setAuditLog(auditLog);
  }
}
