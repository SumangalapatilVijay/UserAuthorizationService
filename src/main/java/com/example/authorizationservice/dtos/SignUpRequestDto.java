package com.example.authorizationservice.dtos;

import com.example.authorizationservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class SignUpRequestDto {
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
