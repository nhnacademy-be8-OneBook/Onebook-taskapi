package com.nhnacademy.taskapi.point.exception;

public class PointPolicyException extends ApplicationException {
    public PointPolicyException(String errorStatus) {
        super(errorStatus);
    }
}
