package com.nhnacademy.taskapi.member.exception;

public class MemberAlreadyExistsException extends IllegalArgumentException {
    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
