package com.nhnacademy.taskapi.publisher.exception;

import jakarta.ws.rs.NotFoundException;

public class PublisherNotFoundException extends NotFoundException {
    public PublisherNotFoundException(String message) {
        super(message);
    }
}
