package com.sass.erp.finance.cash.api_service.http.controllers.resources;
import java.lang.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.sass.erp.finance.cash.api_service.database.factories.UserFactory;
import com.sass.erp.finance.cash.api_service.http.controllers.RestfullApiController;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.entities.generics.system.AsyncTaskEntity;
import com.sass.erp.finance.cash.api_service.models.enums.TaskState;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import com.sass.erp.finance.cash.api_service.services.AsyncTaskService;
import com.sass.erp.finance.cash.api_service.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController extends RestfullApiController {

  protected UserService userService;

  protected AsyncTaskService asyncTaskService;

  protected double totalProgress = 0.0;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected EntityManager entityManager;

  public UserController(
    UserService userService,
    AsyncTaskService asyncTaskService
  ) {
    this.userService = userService;
    this.asyncTaskService = asyncTaskService;
  }

  @GetMapping("/users")
  public HttpEntity<? extends RestfullApiResponse<?,?>> index(
    HttpServletRequest request,
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size
  ) {

    RestfullApiResponse<?, ?> response = this.userService.fetchAllThenPaginate(
      page,
      size,
      request
    );

    return ResponseEntity
      .status(response.getStatusCode())
      .body(response);
  }

  @GetMapping("/users/{uuid}")
  public HttpEntity<? extends RestfullApiResponse<?,?>> show(
    HttpServletRequest request,
    @PathVariable String uuid
  ) {

    RestfullApiResponse<?,?> response = this.userService.fetchUserByUUID(uuid);

    return ResponseEntity
      .status(response.getStatusCode())
      .body(response);
  }

  @PostMapping("/users")
  public HttpEntity<RestfullApiResponse<?, ?>> post(Object attributes){
    RestfullApiResponse<?, ?> response = this.userService.createNewUser(attributes);

    return ResponseEntity
      .status(response.getStatusCode())
      .body(response);
  }

//  @PatchMapping("/users/{uuid}")
//  public HttpEntity<RestfullApiResponse<?, ?>> patch(String id, Object attributes){
//    UserEntity response = this.userService.(id, attributes);
//
//    return ResponseEntity
//      .status(response.getStatusCode())
//      .body(response);
//  }

  @GetMapping("/users/create-async")
  public HttpEntity<RestfullApiResponse<Object, ?>> createAsync(){

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

    RestfullApiResponse<Object, ?> response = RestfullApiResponseFactory.success(data, "Success creating dummy users", HttpStatus.CREATED);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(response);
  }



//  @GetMapping("/users/create/progress/{taskId}")
//  public HttpEntity<RestfullApiResponse<Object, ?>> getTaskProgress(
//    @PathVariable String taskId
//  ) {
//
//    AsyncTaskEntity asyncTaskEntity = this.asyncTaskService
//      .findByUUID(
//        UUID.fromString(taskId).toString()
//      )
//      .orElseThrow(EntityNotFoundException::new);
//
//    Map<String, Object> data = new LinkedHashMap<>();
//    data.put("taskId", asyncTaskEntity.getIdentifier().getUuid().toString());
//    data.put("taskProgress", asyncTaskEntity.getTaskProgress().toString());
//    data.put("taskStatus", asyncTaskEntity.getTaskState().toString());
//
//    RestfullApiResponse<Object,?> response = RestfullApiResponseFactory.success(
//      data,
//      "Task progress retrieved",
//      HttpStatus.OK
//    );
//
//    return ResponseEntity
//      .status(response.getStatusCode())
//      .body(response);
//  }

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
