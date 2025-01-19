package com.sass.erp.finance.cash.api_service.database.factories;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.UUID;

public class UserFactory extends BaseFactory<UserEntity> {

    protected EntityManager entityManager;
    private final UserRepository repository;

    public UserFactory(UserRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public UserEntity definition() {
        UserEntity entity = new UserEntity();
        EmbeddedUUID uuid = new EmbeddedUUID();

        uuid.setUuid(UUID.randomUUID());
        entity.setId(uuid);
        entity.setUserUsername("user" + UUID.randomUUID().toString().hashCode());
        entity.setUserEmail("user"+UUID.randomUUID().toString().hashCode()+"@okay.com");
        entity.setUserPassword("pass" + UUID.randomUUID().toString().hashCode());
        entity.setUserIsActive(true);
        return entity;
    }

    @Override
    protected BaseRepository<UserEntity, ?> getRepository() {
        return this.repository;
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public static UserFactory newInstance(UserRepository repository, EntityManager entityManager){
        return new UserFactory(repository, entityManager);
    }
}
