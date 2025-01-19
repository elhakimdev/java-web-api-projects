package com.sass.erp.finance.cash.api_service.database.factories;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String systemTime = String.valueOf(System.currentTimeMillis());

        UserEntity entity = new UserEntity();
        EmbeddedIdentifier embeddedIdentifier = new EmbeddedIdentifier();
        EmbeddedExternalID embeddedExternalID = new EmbeddedExternalID();

        UUID uuid = UUID.randomUUID();
        embeddedIdentifier.setUuid(uuid);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String generatedCode = now.format(formatter);


        embeddedExternalID.setExternalId("USR-EXT-REF" + generatedCode);
        embeddedExternalID.setSystemRefId("USR-SYS-REF" + generatedCode);
        embeddedExternalID.setUniqueId("USR-U-REF" + generatedCode);
        embeddedExternalID.setDisplayId("USR-DSP-REF" + generatedCode);

        entity.setIdentifier(embeddedIdentifier);
        entity.setExternalIdentifier(embeddedExternalID);
        entity.setUserUsername("KC_MT01" + systemTime);
        entity.setUserEmail("KC_MT01" + systemTime + "@service.com");
        entity.setUserPassword("PASSKIT01" + systemTime);
        entity.setUserIsActive(false);
        entity.setUserIsMigrated(false);
        entity.setUserIsVerified(false);
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

    public UserFactory verifiedUser(){
        return (UserFactory) this.state(user -> {
            user.setUserUsername("KC_MT01VERIFIED");
            user.setUserEmail("KC_MT01VERIFIED@service.com");
            user.setUserPassword("PASSKIT01VERIFIED");
            user.setUserIsVerified(true);
        });
    }
}
