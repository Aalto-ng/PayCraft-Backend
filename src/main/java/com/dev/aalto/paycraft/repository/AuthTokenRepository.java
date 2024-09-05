package com.dev.aalto.paycraft.repository;

import com.dev.aalto.paycraft.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByAccessToken(String authToken);
    List<AuthToken> findAllByAccessTokenByUser_id(Long userId);
}
