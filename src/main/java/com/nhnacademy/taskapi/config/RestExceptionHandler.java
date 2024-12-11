package com.nhnacademy.taskapi.config;

import com.nhnacademy.taskapi.exception.dto.ErrorResponse;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // NotFoundException
    @ExceptionHandler({MemberNotFoundException.class, })
    public ResponseEntity<Object> handleNotFoundException(MemberNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // IllegalArgumentException
    @ExceptionHandler({MemberIllegalArgumentException.class, })
    public ResponseEntity<Object> handleIllegalArgumentException(MemberIllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
