package com.example.authorizationservice.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@JsonDeserialize(as = User.class)
public class User extends BaseModel {
    private String passwordSalt;
    private String email;
    @ManyToMany(fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.passwordSalt = password;
    }

    public User() {

    }
}
