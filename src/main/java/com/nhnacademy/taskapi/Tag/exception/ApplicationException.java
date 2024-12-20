package com.nhnacademy.taskapi.Tag.exception;

import com.nhnacademy.taskapi.exception.dto.ErrorResponse;

public class ApplicationException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public ApplicationException(String title, int status) {
        super(title);
        this.errorResponse = new ErrorResponse(title, status);
    }

    // ErrorResponse 반환 메서드
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
