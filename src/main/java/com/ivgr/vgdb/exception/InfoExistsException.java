package com.ivgr.vgdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InfoExistsException extends RuntimeException {
  public InfoExistsException(String message) {
    super(message);
  }
}
