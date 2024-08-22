package com.example.authorizationservice.exception;

public class PasswordInCorrectMatchException extends  Exception{
   public PasswordInCorrectMatchException(String message) {
        super(message);
    }
}
