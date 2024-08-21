package com.example.authorizationservice.controllers;

import com.example.authorizationservice.dtos.LoginRequestDto;
import com.example.authorizationservice.dtos.LoginResponseDto;
import com.example.authorizationservice.dtos.SignUpRequestDto;
import com.example.authorizationservice.dtos.SignUpResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    public AuthController() {}
    @PostMapping("/sign_up")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto request) {

        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return null;
    }
}
