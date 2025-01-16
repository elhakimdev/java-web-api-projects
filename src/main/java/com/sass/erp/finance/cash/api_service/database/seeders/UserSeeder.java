package com.sass.erp.finance.cash.api_service.database.seeders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sass.erp.finance.cash.api_service.database.factories.UserFactory;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSeeder {

    protected Logger logger = LoggerFactory.getLogger(UserSeeder.class);

    @Autowired
    protected UserFactory factory;

    @Autowired
    protected UserRepository repository;

    public void run(String ...args) throws JsonProcessingException {
        logger.info(
                "Running [{}] to create [{}] records of [{}]",
                this.factory.getClass().getCanonicalName(),
                10,
                this.factory.getEntity().getClass().getCanonicalName());
        this.factory.create(10);
    }
}
