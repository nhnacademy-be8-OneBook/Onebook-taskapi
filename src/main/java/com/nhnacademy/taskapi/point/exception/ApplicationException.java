package com.nhnacademy.taskapi.point.exception;

public abstract class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);  // 부모 클래스에서 메시지를 처리
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);  // 부모 클래스에서 메시지와 원인 처리
    }
}