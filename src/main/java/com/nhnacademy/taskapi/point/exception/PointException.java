package com.nhnacademy.taskapi.point.exception;

import com.nhnacademy.taskapi.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

public class PointException extends ApplicationException {
    private final ErrorResponse errorResponse;

    // NOT_FOUND(404) - 포인트를 찾을 수 없는 경우
    public static PointException pointNotFound() {
        return new PointException("Point not found", HttpStatus.NOT_FOUND);
    }

    // BAD_REQUEST(400) - 잘못된 요청
    public static PointException badRequest(String message) {
        return new PointException(message, HttpStatus.BAD_REQUEST);
    }

    // INTERNAL_SERVER_ERROR(500) - 서버 내부 오류
    public static PointException internalServerError() {
        return new PointException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private PointException(String message, HttpStatus status) {
        super(message);
        this.errorResponse = new ErrorResponse(message, status.value());
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}