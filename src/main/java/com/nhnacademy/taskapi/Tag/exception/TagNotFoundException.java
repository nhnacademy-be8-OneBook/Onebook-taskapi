package com.nhnacademy.taskapi.Tag.exception;

import jakarta.ws.rs.NotFoundException;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(String message) {
        super(message);
    }
}
