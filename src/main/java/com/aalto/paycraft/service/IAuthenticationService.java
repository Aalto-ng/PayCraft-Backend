package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.AuthorizationResponseDto;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.LoginRequestDto;
import com.aalto.paycraft.dto.RefreshTokenRequestDto;

public interface IAuthenticationService {
    DefaultApiResponse<AuthorizationResponseDto> login(LoginRequestDto requestBody);
    DefaultApiResponse<AuthorizationResponseDto> refreshToken(RefreshTokenRequestDto requestBody);
}
