package com.sass.erp.finance.cash.api_service.http.requests.impl;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class UserRequestImpl extends RequestImpl {

  @NotBlank(message = "Username is required", groups = {RequestImpl.Create.class})
  @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters", groups = {RequestImpl.Create.class, RequestImpl.Update.class})
  @Pattern(
    regexp = "^[a-z0-9_-]+$",
    message = "Username can only contain lowercase letters, numbers, underscores (_), and dashes (-)",
    groups = {RequestImpl.Create.class, RequestImpl.Update.class}
  )
  private String username;

  @NotBlank(message = "Email is required", groups = RequestImpl.Create.class)
  @Email(message = "Invalid email format", groups = {RequestImpl.Create.class, RequestImpl.Update.class})
  @Pattern(
    regexp = "^(?!.*\\.{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$",
    message = "Invalid email format or domain",
    groups = {RequestImpl.Create.class, RequestImpl.Update.class}
  )
  @Size(max = 100, message = "Email must be at most 100 characters", groups = {RequestImpl.Create.class, RequestImpl.Update.class})
  private String email;

  @NotBlank(message = "Password is required", groups = RequestImpl.Create.class)
  @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters", groups = {RequestImpl.Create.class, RequestImpl.Update.class})
  @Pattern(
    regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
    message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character",
    groups = {RequestImpl.Create.class, RequestImpl.Update.class}
  )
  private String password;


  @Override
  protected boolean authorize() {
    return true;
  }
}
