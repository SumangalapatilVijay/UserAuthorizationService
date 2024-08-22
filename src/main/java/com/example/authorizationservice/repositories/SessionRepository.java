package com.example.authorizationservice.repositories;

import com.example.authorizationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Override
    <S extends Session> S save(S entity);

}
