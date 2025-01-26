package com.sass.erp.finance.cash.api_service.http.controllers.resources;
import java.lang.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.sass.erp.finance.cash.api_service.database.factories.UserFactory;
import com.sass.erp.finance.cash.api_service.http.resources.PaginateResource;
import com.sass.erp.finance.cash.api_service.http.resources.UserResource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.generics.system.AsyncTaskEntity;
import com.sass.erp.finance.cash.api_service.models.enums.TaskState;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import com.sass.erp.finance.cash.api_service.services.AsyncTaskService;
import com.sass.erp.finance.cash.api_service.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
public class UserController {

  protected Logger logger = LoggerFactory.getLogger(UserController.class);

  protected UserService userService;

  protected AsyncTaskService asyncTaskService;

  protected double totalProgress = 0.0;

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

    this.createAsyncUser(asyncTask);

    this.asyncTaskService.updateState(
      asyncTask, TaskState.IN_PROGRESS
    );

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

  @GetMapping("/users/create/progress/{taskId}")
  public HttpEntity<RestfullApiResponse<?>> getTaskProgress(
    @PathVariable String taskId
  ) {

    AsyncTaskEntity asyncTaskEntity = this.asyncTaskService
      .find(
        UUID.fromString(taskId).toString()
      )
      .orElseThrow(EntityNotFoundException::new);

    Map<String, Object> data = new LinkedHashMap<>();
    data.put("taskId", asyncTaskEntity.getIdentifier().getUuid().toString());
    data.put("taskProgress", asyncTaskEntity.getTaskProgress().toString());
    data.put("taskStatus", asyncTaskEntity.getTaskState().toString());

    RestfullApiResponse<Object> response = RestfullApiResponseFactory.success(
      data,
      "Task progress retrieved",
      HttpStatus.OK
    );

    return ResponseEntity
      .status(response.getStatusCode())
      .body(response);
  }

  @Async
  protected void createAsyncUser(AsyncTaskEntity asyncTaskEntity) {
    CompletableFuture<Void> dummyUsers = CompletableFuture.runAsync(() -> this.createDummyUser(asyncTaskEntity));
//    CompletableFuture<Void> verifiedUsers = CompletableFuture.runAsync(() -> this.createVerifiedUser(asyncTaskEntity));
    CompletableFuture.allOf(dummyUsers).thenRun(() -> {
      this.asyncTaskService.updateState(asyncTaskEntity, TaskState.COMPLETED);
      this.totalProgress = 0.0;
    });
  }

  protected void createDummyUser(AsyncTaskEntity asyncTaskEntity){
    int count = 1000;
    UserFactory userFactory = new UserFactory();
    userFactory.setRepository(userRepository);
    userFactory.setEntityManager(entityManager);
    userFactory.count(count).create(progress -> {
      this.totalProgress = calculateProgress(progress, count);
      if(this.totalProgress == 99.0) {
        this.totalProgress += 1.0;
      }
      asyncTaskService.updateProgress(asyncTaskEntity, this.totalProgress);
    });
  }

  protected void createVerifiedUser(AsyncTaskEntity asyncTaskEntity) {
    int count = 1000;
    UserFactory userFactory = new UserFactory();
    userFactory.setRepository(userRepository);
    userFactory.setEntityManager(entityManager);
    userFactory.verifiedUser().count(count).create(progress -> {
      double createdProgress = calculateProgress(progress, count);
      this.totalProgress = (totalProgress + createdProgress);
      asyncTaskEntity.setTaskProgress(this.totalProgress);
      asyncTaskService.updateProgress(asyncTaskEntity, this.totalProgress);
    });
  }

  protected double calculateProgress(int created, int totalCount) {
    return ((double) created / totalCount * 100);
  }
}
