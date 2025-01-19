package com.sass.erp.finance.cash.api_service.services;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class UserService extends CommonService<UserEntity, EmbeddedIdentifier> {
    @Autowired
    protected UserRepository userRepository;

    public UserService(CrudTaskService<UserEntity, EmbeddedIdentifier> crudTaskService) {
        super(crudTaskService);
    }

    @Override
    protected BaseRepository<UserEntity, EmbeddedIdentifier> getRepository() {
        return this.userRepository;
    }
}
