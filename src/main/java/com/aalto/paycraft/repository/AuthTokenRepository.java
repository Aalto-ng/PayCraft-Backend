package com.aalto.paycraft.repository;

import com.aalto.paycraft.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByAccessToken(String authToken);
    List<AuthToken> findAllByEmployerProfile_EmployerProfileId(UUID employerProfileId);
}
