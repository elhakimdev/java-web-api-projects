package com.sass.erp.finance.cash.api_service.database.factories;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Component
@Scope("prototype")
public class UserFactory extends BaseFactory<UserEntity> {

    @Autowired
    protected UserRepository repository;

    @Autowired
    protected EntityManager entityManager;

    @Override
    public UserEntity definition() {
        UserEntity entity = new UserEntity();
        EmbeddedUUID embeddedUUID = new EmbeddedUUID();
        UUID uuid = UUID.randomUUID();
        String systemTime = String.valueOf(System.currentTimeMillis());
        embeddedUUID.setUuid(uuid);
        entity.setId(embeddedUUID);
        entity.setUserUsername("KC_MT01" + systemTime);
        entity.setUserEmail("KC_MT01" + systemTime + "@service.com");
        entity.setUserPassword("PASSKIT01" + systemTime);
        entity.setUserIsActive(true);
        entity.setUserIsMigrated(true);
        entity.setUserIsVerified(true);
        entity.setUserIsLocked(false);
        entity.setUserIsExternal(false);
        entity.setUserEmailVerifiedAt(LocalDateTime.now());
        return entity;
    }

    @Override
    protected BaseRepository<UserEntity, ?> getRepository() {
        return this.repository;
    }

    protected EntityManager getEntityManager(){
        return this.entityManager;
    }
}
