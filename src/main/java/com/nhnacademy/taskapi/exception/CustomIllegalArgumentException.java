package com.nhnacademy.taskapi.exception;

public class CustomIllegalArgumentException extends IllegalArgumentException {
    public CustomIllegalArgumentException(String message) {
        super(message);
    }
}
