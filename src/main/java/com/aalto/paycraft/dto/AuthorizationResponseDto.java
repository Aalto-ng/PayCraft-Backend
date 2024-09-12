package com.aalto.paycraft.dto;

public record AuthorisationResponseDto(
        String accessToken,
        String refreshToken,
        String issuedAt,
        String accessTokenValidityTime,
        String refreshTokenValidityTime
) {}
