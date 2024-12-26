package com.nhnacademy.taskapi.roles.exception;

import jakarta.ws.rs.NotFoundException;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String message) {
    super(message);
  }
}
