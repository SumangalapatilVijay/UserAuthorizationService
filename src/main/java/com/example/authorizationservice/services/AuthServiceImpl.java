package com.example.authorizationservice.services;

import com.example.authorizationservice.exception.PasswordInCorrectMatchException;
import com.example.authorizationservice.exception.UserAleadyExistWithThisEmailException;
import com.example.authorizationservice.exception.UserEmailNotFoundException;
import com.example.authorizationservice.models.Role;
import com.example.authorizationservice.models.Session;
import com.example.authorizationservice.models.SessionStatus;
import com.example.authorizationservice.models.User;
import com.example.authorizationservice.repositories.SessionRepository;
import com.example.authorizationservice.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final SessionRepository sessionRepository;
    SecretKey key = Jwts.SIG.HS256.key().build();
    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder =bcryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
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
        String token = null;
        if(userRepository.findByEmail(email).isEmpty()) {
            throw new UserEmailNotFoundException("User with email = "+email+"Not Exists in our system");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(bcryptPasswordEncoder.matches(password,optionalUser.get().getPasswordSalt())) {
            token = createJwtToken(optionalUser.get().getEmail(),optionalUser.get().getId(),new ArrayList<>());
            Session session = new Session();
            session.setStatus(SessionStatus.ACTIVE);
            session.setId(optionalUser.get().getId());

            Calendar c= Calendar.getInstance();
            c.add(Calendar.DATE, 30);
            Date d=c.getTime();
            session.setExpiringAt(d);
            session.setCreatedAt(new Date());
            session.setToken(token);
             sessionRepository.save(session);
        } else {
            throw new PasswordInCorrectMatchException("Password is incorrect for Email = "+email);
        }
        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    protected String createJwtToken(String email, Long userId, List<Role> roles) {
        String token = null;
        JwtBuilder builder = Jwts.builder();
    Map<String,Object> jwtClaims = new HashMap<>();
    jwtClaims.put("userId",userId);
    jwtClaims.put("email",email);
    jwtClaims.put("roles",roles);
    builder.claims(jwtClaims);
    builder.signWith(key);
    Calendar c= Calendar.getInstance();
    c.add(Calendar.DATE, 30);
    Date d=c.getTime();
    builder.expiration(d);
    builder.issuedAt(new Date());
    token = builder.compact();
        return token;
}

}
