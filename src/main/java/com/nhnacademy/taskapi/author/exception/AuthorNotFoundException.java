package com.nhnacademy.taskapi.author.exception;

import jakarta.ws.rs.NotFoundException;

public class AuthorNotFoundException extends NotFoundException {
    public AuthorNotFoundException(String message) {super(message);}
}
