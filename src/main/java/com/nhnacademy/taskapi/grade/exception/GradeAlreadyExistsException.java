package com.nhnacademy.taskapi.grade.exception;

public class GradeAlreadyExistsException extends IllegalArgumentException {
  public GradeAlreadyExistsException(String message) {
    super(message);
  }
}
