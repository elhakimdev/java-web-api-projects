package com.sass.erp.finance.cash.api_service.services;
import com.sass.erp.finance.cash.api_service.http.resources.UserResource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService extends CommonService<UserEntity, EmbeddedIdentifier> {
  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected UserResource resource;

  @Override
  protected BaseRepository<UserEntity, EmbeddedIdentifier> getRepository() {
    return this.userRepository;
  }

  @Override
  protected UserEntity updateAttributes(UserEntity entity, Object attributes) {
    entity.setUserEmail("");
    entity.setUserUsername("");

    return entity;
  }

  @Override
  protected UserEntity createAttributes(UserEntity entity, Object attributes) {
    return null;
  }

  public RestfullApiResponse<?, ?> fetchAllThenPaginate(
    int page,
    int size,
    HttpServletRequest request
  ) {
    Page<UserEntity> users = this.get(page - 1, size);

    Map<String, Object> paginatedCollections = this.resource.toPaginatedCollectionsResponse(users, request);

    return RestfullApiResponseFactory.success(
      paginatedCollections,
      "List of users",
      HttpStatus.OK
    );
  }

  public RestfullApiResponse<?, ?> fetchUserByUUID(String uuid) {

    UserEntity user = this.find(uuid);

    return RestfullApiResponseFactory.success(
      this.resource.toResponse(user),
      "User by id: [" + uuid + "]",
      HttpStatus.OK
    );
  }

  public RestfullApiResponse<?,?> createNewUser(Object attributes) {

    UserEntity entity = new UserEntity();

    UserEntity newUser = this.create(entity, attributes);

    return RestfullApiResponseFactory.success(
      this.resource.toResponse(newUser),
      "User created successfully",
      HttpStatus.OK
    );
  }
}
