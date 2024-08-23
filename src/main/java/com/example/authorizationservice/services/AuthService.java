package com.example.authorizationservice.services;

import com.example.authorizationservice.exception.PasswordInCorrectMatchException;
import com.example.authorizationservice.exception.UserAleadyExistWithThisEmailException;
import com.example.authorizationservice.exception.UserEmailNotFoundException;

public interface AuthService {
     boolean signUp(String email, String password) throws UserAleadyExistWithThisEmailException;
     String login(String email, String password) throws UserEmailNotFoundException, PasswordInCorrectMatchException;

     boolean validate(String token);
}
