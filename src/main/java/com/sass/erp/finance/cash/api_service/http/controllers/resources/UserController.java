package com.sass.erp.finance.cash.api_service.http.controllers.resources;
import java.lang.*;

import com.sass.erp.finance.cash.api_service.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/users")
public class UserController {

    public UserController(UserService userService) {
    }
}
