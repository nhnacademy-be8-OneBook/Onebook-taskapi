package com.nhnacademy.taskapi.book.exception;

import jakarta.ws.rs.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {super(message);}
}
