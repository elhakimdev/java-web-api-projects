package com.sass.erp.finance.cash.api_service.http.controllers.resources;
import java.lang.*;

import com.sass.erp.finance.cash.api_service.services.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/users")
public class UserController {

  private UserServiceImpl userService;

  public UserController(){
  }
}
