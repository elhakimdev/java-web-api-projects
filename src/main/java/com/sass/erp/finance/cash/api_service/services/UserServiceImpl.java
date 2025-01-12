package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.models.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  protected UserRepository userRepository;
}
