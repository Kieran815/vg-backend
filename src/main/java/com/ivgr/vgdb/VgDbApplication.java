package com.ivgr.vgdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// The @SpringBootApplication annotation performs the following 3 operation for us:
  //@Configure:
    // configure embedded tomcat for us
  //@EnableAutoConfiguration
    // Enable spring mvc default setup.
  //@ComponentScan
    // Configure and setup the Jackson for JSON
@SpringBootApplication
public class VgDbApplication {

  public static void main(String[] args) {
    SpringApplication.run(VgDbApplication.class, args);
  }

}
