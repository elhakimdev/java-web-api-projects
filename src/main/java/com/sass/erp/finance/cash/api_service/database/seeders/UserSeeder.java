package com.sass.erp.finance.cash.api_service.database.seeders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sass.erp.finance.cash.api_service.database.factories.UserFactory;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSeeder extends BaseSeeder {


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EntityManager entityManager;

    @Override
    @Transactional
    public void run() {
        UserFactory userFactory = UserFactory.newInstance(this.userRepository, this.entityManager);
        userFactory.count(100).create();
    }
}
