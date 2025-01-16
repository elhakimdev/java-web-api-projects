package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;
import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
  protected UserRepository userRepository;

  public List<UserEntity> fetchAllUsers(){
    return userRepository.findAll();
  }
}
