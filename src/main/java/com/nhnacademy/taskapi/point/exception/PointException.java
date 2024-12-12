package com.nhnacademy.taskapi.point.exception;

import com.nhnacademy.taskapi.point.exception.ErrorStatus;

public class PointException extends ApplicationException {
    public PointException(ErrorStatus errorStatus) {
        super(String.valueOf(errorStatus));
    }
}
