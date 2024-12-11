package com.nhnacademy.taskapi.config;

import com.nhnacademy.taskapi.exception.CustomIllegalArgumentException;
import com.nhnacademy.taskapi.exception.CustomNotFoundException;
import com.nhnacademy.taskapi.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Object> handleCustomNotFoundException(CustomNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<Object> handleCustomIllegalArgumentException(CustomIllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse("직접 입력해도 됩니다.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
