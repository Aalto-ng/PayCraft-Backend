package com.aalto.paycraft.dto;

public record AuthorizationResponseDto(
        String accessToken,
        String refreshToken,
        String issuedAt,
        String accessTokenValidityTime,
        String refreshTokenValidityTime
) {}
