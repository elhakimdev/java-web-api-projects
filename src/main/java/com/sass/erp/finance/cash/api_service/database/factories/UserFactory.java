package com.sass.erp.finance.cash.api_service.database.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserFactory extends BaseFactory<UserEntity> {

    protected Logger logger = LoggerFactory.getLogger(UserFactory.class);

    protected BCryptPasswordEncoder encoder;

    @Getter
    @Setter
    protected String password = "";

    @Getter
    protected String defaultPassword = "password";

    @Getter
    @Setter
    protected UserEntity entity;

    @Autowired
    protected UserRepository repository;

    public UserFactory() {
        super();
        this.entity = new UserEntity();
        this.encoder = new BCryptPasswordEncoder(16);
        this.password = this.encoder.encode(this.getPassword().isEmpty() ? this.getDefaultPassword() : this.getPassword());
    }

    @Override
    public void definition() throws JsonProcessingException {
        // Setting metadata
        this.id.setId(UUID.randomUUID());
        this.timeStamp.setCreatedAt(LocalDateTime.now());
        this.timeStamp.setUpdatedAt(LocalDateTime.now());
        this.auditLog.setCreatedBy(BaseEntity.DEFAULT_AUDITOR_FLAG.concat(":".concat(UserFactory.class.getName())));

        // Setting User Entity
        this.entity.setUserUsername(this.faker.name().fullName());
        this.entity.setUserEmail(this.faker.internet().safeEmailAddress());
        this.entity.setUserPassword(this.getPassword());
        this.entity.setUserIsActive(this.faker.bool().bool());
        this.entity.setId(this.getId());
        this.entity.setTimestamp(this.getTimeStamp());
        this.entity.setAuditLog(this.getAuditLog());
        this.logger.info("Creating: [{}]", getObjectMapper().writeValueAsString(this.getEntity()));

        // Persist
        this.repository.save(this.getEntity());
    }
}
