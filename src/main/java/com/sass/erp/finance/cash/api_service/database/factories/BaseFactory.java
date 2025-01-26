package com.sass.erp.finance.cash.api_service.database.factories;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;

@Component
@Scope("prototype")
public abstract class BaseFactory<T extends BaseEntity> {

  /**
   * The logger instance
   */
  protected Logger logger = LoggerFactory.getLogger(BaseFactory.class);

  /**
   * The entity manager instance.
   */
  @Autowired
  protected EntityManager entityManager;

  /**
   * The default count of generated entity.
   */
  private static final int DEFAULT_COUNT = 1;

  /**
   * The current count state.
   */
  protected int count = 0;

  /**
   * The entity states.
   */
  protected List<Consumer<T>> states = new ArrayList<>();

  /**
   * The faker instance.
   */
  private final Faker faker = new Faker(Locale.of("id"));

  /**
   * Proxy method for define the entity.
   *
   * @return The entity instance.
   */
  public T make(){
    return definition();
  }

  /**
   * Proxy method for running factory
   */
  public void create(){
    if (this.count == 0) {
      this.create(DEFAULT_COUNT);
    } else {
      this.create(this.count);
    }
  }

  /**
   * Create and persist the entity.
   *
   * @param count The number of entity should be created.
   */
  public void create(int count){
    logger.info("Called create");
    for (int i = 0; i < count; i++) {
      T entity = preDefineIdentifier((prePersist(make())));
      states.forEach(state -> state.accept(entity));
      logger.info("Creating entity: {} with value: {}", entity.getClass(), entity);
      getRepository().saveAndFlush(entity);
      logger.info("Saving entity: {} with value: {}", entity.getClass(), entity);
    }

    getRepository().flush();
    this.getEntityManager().clear();
    this.count = DEFAULT_COUNT;
  }

  /**
   * The number of count factory should generate the entity.
   *
   * @param count The number of created entity.
   * @return The factory instance.
   */
  public BaseFactory<T> count(int count) {
    this.count = count;
    return this;
  }

  /**
   * Add entity state definition.
   *
   * @param state Entity state.
   * @return The factory instance.
   */
  public BaseFactory<T> state(Consumer<T> state) {
    states.add(state);
    return this;
  }

  /**
   * Get the faker library.
   *
   * @return The faker library instance.
   */
  protected Faker getFaker() {
    return this.faker;
  }

  /**
   * Hooks before persist the entity with predefine identifier.
   *
   * @param entity The entity which will be persisted.
   * @return The target entity.
   */
  protected T preDefineIdentifier(T entity) {
    String systemTime = String.valueOf(System.currentTimeMillis());

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

  /**
   * Hooks before persist the entity with predefine identifier.
   *
   * @param entity The entity which will be persisted.
   * @return The target entity.
   */
  protected T prePersist(T entity) {
    EmbeddedAuditLog auditLog = new EmbeddedAuditLog();
    EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();

    timeStamp.setCreatedAt(LocalDateTime.now());
    timeStamp.setUpdatedAt(LocalDateTime.now());
    timeStamp.setRestoredAt(null);
    timeStamp.setDeletedAt(null);

    auditLog.setCreatedBy("SYSTEM_SEEDER");
    auditLog.setLastUpdatedBy("SYSTEM_SEEDER");
    auditLog.setLastRestoredBy(null);
    auditLog.setLastDeletedBy(null);

    entity.setAuditLog(auditLog);
    entity.setTimeStamp(timeStamp);
    return entity;
  }

  /**
   * Define the entity.
   *
   * @return The entity.
   */
  protected abstract T definition();

  /**
   * Get the repository for current entity instance.
   *
   * @return The entity repository instance.
   */
  protected abstract BaseRepository<T, ?> getRepository();

  /**
   * Get entity manager instance.
   *
   * @return The entity manager instnce.
   */
  protected abstract EntityManager getEntityManager();
}
