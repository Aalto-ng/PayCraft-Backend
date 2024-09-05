package com.dev.aalto.paycraft.controller;

import com.dev.aalto.paycraft.dto.AuthorisationResponseDto;
import com.dev.aalto.paycraft.dto.DefaultApiResponse;
import com.dev.aalto.paycraft.dto.LoginRequestDto;
import com.dev.aalto.paycraft.dto.RefreshTokenRequestDto;
import com.dev.aalto.paycraft.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j @RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {

    // Service layer dependency to handle authentication-related operations.
    private final AuthenticationService authenticationService;

    /**
     * Endpoint for user login.
     * @param request contains the login credentials.
     * @return a response containing the authorization details (e.g., access authToken) if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<DefaultApiResponse<AuthorisationResponseDto>> login(@RequestBody @Validated LoginRequestDto request){
        DefaultApiResponse<AuthorisationResponseDto> response = authenticationService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint for refreshing the access authToken using a refresh authToken.
     * @param request contains the refresh authToken details.
     * @return a response containing the new authorization details (e.g., new access authToken).
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<DefaultApiResponse<AuthorisationResponseDto>> refreshToken(@RequestBody @Validated RefreshTokenRequestDto request){
        DefaultApiResponse<AuthorisationResponseDto> response = authenticationService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
