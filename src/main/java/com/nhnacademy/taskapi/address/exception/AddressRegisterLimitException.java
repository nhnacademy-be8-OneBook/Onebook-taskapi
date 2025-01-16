package com.nhnacademy.taskapi.address.exception;

public class AddressRegisterLimitException extends RuntimeException {
    public AddressRegisterLimitException(String message) {
        super(message);
    }
}
