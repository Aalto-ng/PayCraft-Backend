package com.dev.aalto.paycraft.service;

import com.dev.aalto.paycraft.dto.AuthorisationResponseDto;
import com.dev.aalto.paycraft.dto.DefaultApiResponse;
import com.dev.aalto.paycraft.dto.LoginRequestDto;
import com.dev.aalto.paycraft.dto.RefreshTokenRequestDto;

public interface AuthenticationService {
    DefaultApiResponse<AuthorisationResponseDto> login(LoginRequestDto requestBody);
    DefaultApiResponse<AuthorisationResponseDto> refreshToken(RefreshTokenRequestDto requestBody);
}
