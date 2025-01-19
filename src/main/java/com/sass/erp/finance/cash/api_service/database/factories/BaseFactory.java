package com.sass.erp.finance.cash.api_service.database.factories;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@Scope("prototype")
public abstract class BaseFactory<T extends BaseEntity> {

    protected Logger logger = LoggerFactory.getLogger(BaseFactory.class);

    @Autowired
    protected EntityManager entityManager;

    private static final int DEFAULT_COUNT = 1;

    protected int count = 0;

    protected List<Consumer<T>> states = new ArrayList<>();

    public T make(){
        return definition();
    }

    public void create(){
        if (this.count == 0) {
            this.create(DEFAULT_COUNT);
        } else {
            this.create(this.count);
        }
    }

    public void create(int count){
        logger.info("Called create");
        for (int i = 0; i < count; i++) {
            T entity = prePersist(make());
            logger.info("Creating entity: {} with value: {}", entity.getClass(), entity);
            getRepository().saveAndFlush(entity);
            logger.info("Saving entity: {} with value: {}", entity.getClass(), entity);
        }

        getRepository().flush();
        this.getEntityManager().clear();
        this.count = DEFAULT_COUNT;
    }

    public BaseFactory<T> count(int count) {
        this.count = count;
        return this;
    }

    public BaseFactory<T> state(Consumer<T> state) {
        states.add(state);
        return this;
    }

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

    protected abstract T definition();
    protected abstract BaseRepository<T, ?> getRepository();
    protected abstract EntityManager getEntityManager();
}
