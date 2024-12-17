package com.nhnacademy.taskapi.member.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class MemberDataIntegrityViolationException extends DataIntegrityViolationException {
    public MemberDataIntegrityViolationException(String message) {
        super(message);
    }
}
