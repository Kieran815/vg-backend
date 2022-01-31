package com.ivgr.vgdb.service;

import com.ivgr.vgdb.exception.InfoExistsException;
import com.ivgr.vgdb.model.Request.LoginRequest;
import com.ivgr.vgdb.model.Response.LoginResponse;
import com.ivgr.vgdb.model.User;
import com.ivgr.vgdb.repository.UserRepository;
import com.ivgr.vgdb.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
  private UserRepository userRepository;
  private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTUtils jwtUtils; // sends JWT token

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User userObject) {
    LOGGER.info("calling createUser method from service");
    if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
      userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
      return userRepository.save(userObject);
    } else {
      throw new InfoExistsException("user with email address " +
          userObject.getEmailAddress() + " is already exists");
    }
  }

  public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
    System.out.println("service calling loginUser ==>");
    authenticationManager.authenticate(new
        UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
    final String JWT = jwtUtils.generateToken(userDetails);
    return ResponseEntity.ok(new LoginResponse(JWT));
  }

  public User findUserByEmailAddress(String email) {
    return userRepository.findUserByEmailAddress(email);
  }
}