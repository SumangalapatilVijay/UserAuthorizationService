package com.example.authorizationservice.services;

import com.example.authorizationservice.exception.PasswordInCorrectMatchException;
import com.example.authorizationservice.exception.UserAleadyExistWithThisEmailException;
import com.example.authorizationservice.exception.UserEmailNotFoundException;
import com.example.authorizationservice.models.User;
import com.example.authorizationservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    public AuthServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder =bcryptPasswordEncoder;
    }
    @Override
    public boolean signUp(String email, String password) throws UserAleadyExistWithThisEmailException {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new UserAleadyExistWithThisEmailException("User Already exists with this email id");
        }
        //salt the password
        String passwordSalt = bcryptPasswordEncoder.encode(password);
        User user = new User(email, passwordSalt);
        userRepository.save(user);
        return true;
    }

    @Override
    public String login(String email, String password) throws UserEmailNotFoundException, PasswordInCorrectMatchException {
        if(userRepository.findByEmail(email).isEmpty()) {
            throw new UserEmailNotFoundException("User with email = "+email+"Not Exists in our system");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(bcryptPasswordEncoder.matches(password,optionalUser.get().getPasswordSalt())) {
            //Jwts.builder().signWith()
        } else {
            throw new PasswordInCorrectMatchException("Password is incorrect for Email = "+email);
        }
        return "token";
    }

}
