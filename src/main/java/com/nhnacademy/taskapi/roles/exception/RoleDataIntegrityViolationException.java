package com.nhnacademy.taskapi.roles.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class RoleDataIntegrityViolationException extends DataIntegrityViolationException {
    public RoleDataIntegrityViolationException(String message) {
        super(message);
    }
}
