package com.example.authorizationservice.exception;

public class UserAleadyExistWithThisEmailException extends Exception {
    public UserAleadyExistWithThisEmailException(String message) {
        super(message);

    }
}
