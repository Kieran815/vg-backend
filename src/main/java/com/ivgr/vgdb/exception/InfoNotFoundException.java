package com.ivgr.vgdb.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InfoNotFoundException extends RuntimeException {
  public InfoNotFoundException(String message) {
    super(message);
  }
}
