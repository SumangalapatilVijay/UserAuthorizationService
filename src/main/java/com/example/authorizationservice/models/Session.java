package com.example.authorizationservice.models;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@JsonDeserialize(as = Session.class)
public class Session extends BaseModel{
    @ManyToOne
    private User user;
    private String token;
    private Date expiringAt;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;

}
