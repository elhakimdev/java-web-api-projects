package com.sass.erp.finance.cash.api_service.services;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CommonService<UserEntity, EmbeddedIdentifier> {
  @Autowired
  protected UserRepository userRepository;

  @Override
  protected BaseRepository<UserEntity, EmbeddedIdentifier> getRepository() {
    return this.userRepository;
  }
}
