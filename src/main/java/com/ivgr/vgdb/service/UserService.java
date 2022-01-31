package com.ivgr.vgdb.service;

import com.ivgr.vgdb.exception.InfoExistsException;
import com.ivgr.vgdb.model.User;
import com.ivgr.vgdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User userObject) {
    System.out.println("service calling createUser ==>");
    if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
      userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
      return userRepository.save(userObject);
    } else {
      throw new InfoExistsException("Email Address " + userObject.getEmailAddress() +
          " Already in Use.");
    }
  }
  public User findUserByEmailAddress(String email) {
    return userRepository.findUserByEmailAddress(email);
  }
}
