package com.nhnacademy.taskapi.point.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String errorStatus;

    public ApplicationException(String errorStatus) {
        this.errorStatus = errorStatus;
    }
}