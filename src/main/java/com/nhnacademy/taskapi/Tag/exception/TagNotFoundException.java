package com.nhnacademy.taskapi.Tag.exception;

import jakarta.ws.rs.NotFoundException;

public class TagNotFoundException extends NotFoundException {
    public TagNotFoundException(String message) {
        super(message);
    }
}
