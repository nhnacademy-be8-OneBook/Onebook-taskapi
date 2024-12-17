package com.nhnacademy.taskapi.roles.exception;

public class RoleAlreadyExistsException extends IllegalArgumentException {
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
