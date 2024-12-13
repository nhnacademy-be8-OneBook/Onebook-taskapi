package com.nhnacademy.taskapi.exception.handler;

import com.nhnacademy.taskapi.point.exception.PointException;
import com.nhnacademy.taskapi.point.exception.PointPolicyException;
import com.nhnacademy.taskapi.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PointException.class)
    public ResponseEntity<ErrorResponse> handlePointException(PointException ex) {
        // PointException이 발생하면 errorResponse를 포함하여 반환
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.valueOf(ex.getErrorResponse().getStatus()));
    }

    @ExceptionHandler(PointPolicyException.class)
    public ResponseEntity<ErrorResponse> handlePointPolicyException(PointPolicyException ex) {
        // PointPolicyException이 발생하면 errorResponse를 포함하여 반환
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.valueOf(ex.getErrorResponse().getStatus()));
    }
}
