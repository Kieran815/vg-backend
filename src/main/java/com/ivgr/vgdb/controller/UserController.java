package com.ivgr.vgdb.controller;

import com.ivgr.vgdb.model.Request.LoginRequest;
import com.ivgr.vgdb.model.User;
import com.ivgr.vgdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/auth/users")
public class UserController {
  private UserService userService;
  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  // User Registration Link
  @PostMapping("/register")
  public User createUser(@RequestBody User userObject) {
    LOGGER.info("Calling createUser from Controller.");
    return userService.createUser(userObject);
  }

  // User Login Link
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
    LOGGER.info("Calling loginUser...");
    return userService.loginUser(loginRequest); // `loginUser` built in `userService`
  }
}
