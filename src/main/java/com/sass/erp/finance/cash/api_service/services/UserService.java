package com.sass.erp.finance.cash.api_service.services;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class UserService extends CommonService<UserEntity, EmbeddedUUID> {
    @Autowired
    protected UserRepository userRepository;

    public UserService(CrudTaskService<UserEntity, EmbeddedUUID> crudTaskService) {
        super(crudTaskService);
    }

    @Override
    protected BaseRepository<UserEntity, EmbeddedUUID> getRepository() {
        return this.userRepository;
    }

    public UserEntity createDefaultUser(){
        EmbeddedUUID uuid = new EmbeddedUUID();
        uuid.setUuid(UUID.randomUUID());

        EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();
        timeStamp.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Jakarta")));
        timeStamp.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Jakarta")));
        timeStamp.setDeletedAt(null);
        timeStamp.setUpdatedAt(null);

        EmbeddedAuditLog auditLog = new EmbeddedAuditLog();
        auditLog.setCreatedBy("SYSTEM");
        auditLog.setLastUpdatedBy("SYSTEM");
        auditLog.setLastDeletedBy(null);
        auditLog.setLastRestoredBy(null);

        UserEntity user = new UserEntity();
        user.setId(uuid);
        user.setUserUsername("hakimkocak");
        user.setUserEmail("Hakim@hakim.com");
        user.setUserIsActive(true);
        user.setUserPassword("hallo");
        user.setTimeStamp(timeStamp);
        user.setAuditLog(auditLog);
        return this.getRepository().save(user);
    }
}
