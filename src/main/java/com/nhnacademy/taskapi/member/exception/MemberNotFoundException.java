package com.nhnacademy.taskapi.member.exception;


import jakarta.ws.rs.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
