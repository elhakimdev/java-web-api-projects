package com.sass.erp.finance.cash.api_service.http.controllers.resources;
import java.lang.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.sass.erp.finance.cash.api_service.database.factories.UserFactory;
import com.sass.erp.finance.cash.api_service.http.resources.PaginateResource;
import com.sass.erp.finance.cash.api_service.http.resources.UserResource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.generics.system.AsyncTaskEntity;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import com.sass.erp.finance.cash.api_service.services.AsyncTaskService;
import com.sass.erp.finance.cash.api_service.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
public class UserController {

  protected Logger logger = LoggerFactory.getLogger(UserController.class);

  protected UserService userService;

  protected AsyncTaskService asyncTaskService;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected EntityManager entityManager;

  public UserController(
    UserService userService, AsyncTaskService asyncTaskService
  ) {
    this.userService = userService;
    this.asyncTaskService = asyncTaskService;
  }

  @GetMapping("/users")
  public HttpEntity<RestfullApiResponse<Map<String, Object>>> index(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size,
    HttpServletRequest request
  ) {
    Page<UserEntity> users = this.userService.index(page - 1, size);

    List<HashMap<String, Object>> userList = users
      .stream()
      .map(user -> {
        UserResource userResource = new UserResource(user);
        return userResource.toResponse();
      }).toList();

    Map<String, Object> paginateResponse = PaginateResource.toPaginateResponse(
      request,
      userList,
      users,
      "users"
    );

    RestfullApiResponse<Map<String, Object>> response = RestfullApiResponseFactory.success(
      paginateResponse,
      "List of all users data test",
      HttpStatus.OK
    );

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(response);
  }

  @GetMapping("/users/create")
  public HttpEntity<RestfullApiResponse<?>> create(){

    AsyncTaskEntity asyncTask = this.asyncTaskService.createNewAsyncTask("create_batch_user");

    this.createAsyncUser();

    HashMap<String, Object> data = new HashMap<>();

    data.put("taskId", asyncTask.getIdentifier().getUuid().toString());
    data.put("taskName", asyncTask.getTaskName());
    data.put("taskStatus", asyncTask.getTaskState().getState());
    data.put("taskProgress", asyncTask.getTaskProgress());

    RestfullApiResponse<Object> response = RestfullApiResponseFactory.success(data, "Success creating dummy users", HttpStatus.CREATED);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(response);
  }

  @Async
  protected void createAsyncUser() {
    CompletableFuture<Void> dummyUsers = CompletableFuture.runAsync(this::createDummyUser);
    CompletableFuture<Void> verifiedUsers = CompletableFuture.runAsync(this::createVerifiedUser);
    CompletableFuture.allOf(dummyUsers, verifiedUsers);
  }

  protected void createDummyUser(){
    int count = 1000;
    UserFactory userFactory = new UserFactory();
    userFactory.setRepository(userRepository);
    userFactory.setEntityManager(entityManager);
    userFactory.count(count).create(progress -> {
      double createdProgress = calculateProgress(progress, count);

    });
  }

  protected void createVerifiedUser() {
    int count = 1000;
    UserFactory userFactory = new UserFactory();
    userFactory.setRepository(userRepository);
    userFactory.setEntityManager(entityManager);
    userFactory.verifiedUser().count(count).create(progress -> {
      double createdProgress = calculateProgress(progress, count);
    });
  }

  protected double calculateProgress(int created, int totalCount) {
    return ((double) created / totalCount * 100);
  }
}
