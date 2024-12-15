package com.nhnacademy.taskapi.config;

import com.nhnacademy.taskapi.exception.dto.ErrorResponse;
import com.nhnacademy.taskapi.grade.exception.GradeAlreadyExistsException;
import com.nhnacademy.taskapi.grade.exception.GradeIllegalArgumentException;
import com.nhnacademy.taskapi.grade.exception.GradeNotFoundException;
import com.nhnacademy.taskapi.member.exception.MemberAlreadyExistsException;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // NotFoundException
    @ExceptionHandler({MemberNotFoundException.class, GradeNotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // IllegalArgumentException
    @ExceptionHandler({MemberIllegalArgumentException.class,
            GradeIllegalArgumentException.class,
            MemberAlreadyExistsException.class,
            GradeAlreadyExistsException.class
    })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("DTO 유효성 검사 실패", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
