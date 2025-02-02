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

    entity.setUsername(username);
    entity.setEmail(username + "@service.com");
    entity.setPassword(encodedPass);
    entity.setIsActive(false);
    entity.setIsMigrated(false);
    entity.setIsVerified(false);
    entity.setIsLocked(false);
    entity.setIsExternal(false);
    entity.setEmailVerifiedAt(LocalDateTime.now());
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
      user.setUsername("KC_MT01VERIFIED");
      user.setEmail("KC_MT01VERIFIED@service.com");
      user.setPassword("PASSKIT01VERIFIED");
      user.setIsVerified(true);
    });
  }
}
