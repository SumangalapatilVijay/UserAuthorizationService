package com.example.authorizationservice.controllers;

import com.example.authorizationservice.dtos.*;
import com.example.authorizationservice.exception.PasswordInCorrectMatchException;
import com.example.authorizationservice.exception.UserAleadyExistWithThisEmailException;
import com.example.authorizationservice.exception.UserEmailNotFoundException;
import com.example.authorizationservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/sign_up")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto request) {
        SignUpResponseDto response = new SignUpResponseDto();
        try {
            if(authService.signUp(request.getEmail(),request.getPassword())) {
                response.setStatus(ResponseStatus.SUCCESS);
            } else {
                response.setStatus(ResponseStatus.FAILURE);
            }
        } catch (UserAleadyExistWithThisEmailException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        ResponseEntity<LoginResponseDto> response = null;
        String token = null;
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try {
            token = authService.login(request.getEmail(),request.getPassword());
        } catch (UserEmailNotFoundException | PasswordInCorrectMatchException e) {
            loginResponseDto.setStatus(ResponseStatus.FAILURE);
            response = new ResponseEntity<>(loginResponseDto,null, HttpStatus.UNAUTHORIZED);
        }
        if(token != null) {
            loginResponseDto.setStatus(ResponseStatus.SUCCESS);
            MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
            header.set("auth_token", token);
            response = new ResponseEntity<>(loginResponseDto,header, HttpStatus.OK);
        } else {
            loginResponseDto.setStatus(ResponseStatus.FAILURE);
            response = new ResponseEntity<>(loginResponseDto,null, HttpStatus.UNAUTHORIZED);
        }
        return response;
    }


}
