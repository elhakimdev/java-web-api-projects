package com.sass.erp.finance.cash.api_service.database.factories;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedExternalID;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.Setter;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    UserEntity entity = new UserEntity();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String defaultPassword = "PASSKIT01";
    String encodedPass = encoder.encode(defaultPassword);
    Faker faker = this.getFaker();

    // Generate KCMT00000 format
    String generalCode = "KCMT";
    String sequenceCode = String.format("%05d", faker.number().numberBetween(0, 99999));
    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

    String username = generalCode + sequenceCode + date + time;

    entity.setUserUsername(username);
    entity.setUserEmail(username + "@service.com");
    entity.setUserPassword(encodedPass);
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
