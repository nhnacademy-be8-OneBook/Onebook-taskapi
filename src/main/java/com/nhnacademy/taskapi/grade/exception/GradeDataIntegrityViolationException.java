package com.nhnacademy.taskapi.grade.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class GradeDataIntegrityViolationException extends DataIntegrityViolationException {
    public GradeDataIntegrityViolationException(String message) {
        super(message);
    }
}
