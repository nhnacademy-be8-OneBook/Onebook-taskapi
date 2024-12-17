package com.nhnacademy.taskapi.roles.exception;

import jakarta.ws.rs.NotFoundException;

public class RoleNotFoundException extends NotFoundException {
  public RoleNotFoundException(String message) {
    super(message);
  }
}
