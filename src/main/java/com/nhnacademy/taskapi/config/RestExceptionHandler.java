package com.nhnacademy.taskapi.config;

import com.nhnacademy.taskapi.cart.exception.CartIllegalArgumentException;
import com.nhnacademy.taskapi.cart.exception.CartNotFoundException;
import com.nhnacademy.taskapi.exception.dto.ErrorResponse;
import com.nhnacademy.taskapi.grade.exception.GradeAlreadyExistsException;
import com.nhnacademy.taskapi.grade.exception.GradeDataIntegrityViolationException;
import com.nhnacademy.taskapi.grade.exception.GradeIllegalArgumentException;
import com.nhnacademy.taskapi.grade.exception.GradeNotFoundException;
import com.nhnacademy.taskapi.member.exception.MemberAlreadyExistsException;
import com.nhnacademy.taskapi.member.exception.MemberDataIntegrityViolationException;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.roles.exception.RoleAlreadyExistsException;
import com.nhnacademy.taskapi.roles.exception.RoleDataIntegrityViolationException;
import com.nhnacademy.taskapi.roles.exception.RoleIllegalArgumentException;
import com.nhnacademy.taskapi.roles.exception.RoleNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // NotFoundException
    @ExceptionHandler({
            MemberNotFoundException.class,
            GradeNotFoundException.class,
            RoleNotFoundException.class,
            CartNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(ChangeSetPersister.NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // IllegalArgumentException
    @ExceptionHandler({
            MemberIllegalArgumentException.class,
            GradeIllegalArgumentException.class,
            RoleIllegalArgumentException.class,
            CartIllegalArgumentException.class,
            MemberAlreadyExistsException.class,
            GradeAlreadyExistsException.class,
            RoleAlreadyExistsException.class,

    })
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse("DTO 유효성 검사 실패", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // DataIntegrityViolationException
    @ExceptionHandler({
            MemberDataIntegrityViolationException.class,
            GradeDataIntegrityViolationException.class,
            RoleDataIntegrityViolationException.class,
    })
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
