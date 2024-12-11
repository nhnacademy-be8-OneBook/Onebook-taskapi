package com.nhnacademy.taskapi.exception;

import jakarta.ws.rs.NotFoundException;

public class CustomNotFoundException extends NotFoundException {
  public CustomNotFoundException(String message) {
    super(message);
  }
}
