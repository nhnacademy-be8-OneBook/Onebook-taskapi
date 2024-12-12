package com.nhnacademy.taskapi.point.exception;

public class PointException extends ApplicationException {
    public PointException(ErrorStatus errorStatus) {
        super(String.valueOf(errorStatus));
    }
}
