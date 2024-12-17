package com.nhnacademy.taskapi.grade.exception;


import jakarta.ws.rs.NotFoundException;

public class GradeNotFoundException extends NotFoundException {
    public GradeNotFoundException(String message) {
        super(message);
    }
}
