package com.sass.erp.finance.cash.api_service.http.controllers.resources;
import java.lang.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.sass.erp.finance.cash.api_service.database.seeders.DatabaseSeeder;
import com.sass.erp.finance.cash.api_service.http.resources.UserResource;
import com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponse;
import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sass.erp.finance.cash.api_service.http.utils.RestfullApiResponseFactory.*;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
public class UserController {

    protected Logger logger = LoggerFactory.getLogger(UserController.class);

    protected UserService userService;

    @Autowired
    protected DatabaseSeeder seeder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public HttpEntity<RestfullApiResponse<List<HashMap<String, Object>>>> index(){
        logger.info("get all user from service");
        List<UserEntity> users = this.userService.getCrudTaskService().read();
        logger.info("users: {} {}", users.toArray(), "data");

        if(users.toArray().length == 0) {
            this.userService.createDefaultUser();
        }

        List<HashMap<String, Object>> userList = users.stream().map(user -> {
            UserResource userResource = new UserResource(user);
            logger.info("log here: {}", userResource);
            return userResource.toResponse();
        }).toList();

        return ResponseEntity.ok(success(userList, "List of all users data test", Optional.of(HttpStatus.OK)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<RestfullApiResponse<HashMap<String, Object>>> show(){
        UserEntity defaultUser = this.userService.createDefaultUser();

        UserResource userResource = new UserResource(defaultUser);
        return ResponseEntity.ok(success(userResource.toResponse(), "List of all users", Optional.of(HttpStatus.OK)));
    }

    @GetMapping("/users/create")
    public void create(){
        seeder.run();
    }
}
