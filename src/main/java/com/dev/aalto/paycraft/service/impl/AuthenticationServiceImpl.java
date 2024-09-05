package com.dev.aalto.paycraft.service.impl;

import com.dev.aalto.paycraft.dto.AuthorisationResponseDto;
import com.dev.aalto.paycraft.dto.DefaultApiResponse;
import com.dev.aalto.paycraft.dto.LoginRequestDto;
import com.dev.aalto.paycraft.dto.RefreshTokenRequestDto;
import com.dev.aalto.paycraft.entity.AuthToken;
import com.dev.aalto.paycraft.entity.UserAccount;
import com.dev.aalto.paycraft.repository.AuthTokenRepository;
import com.dev.aalto.paycraft.repository.UserAccountRepository;
import com.dev.aalto.paycraft.service.AuthenticationService;
import com.dev.aalto.paycraft.service.JwtService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.dev.aalto.paycraft.constant.PayCraftConstant.*;

@Slf4j @Service @RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAccountRepository userRepository;
    private final AuthTokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private record accessAndRefreshToken(String accessToken, String refreshToken) {}

    @Override
    public DefaultApiResponse<AuthorisationResponseDto> login(LoginRequestDto requestBody) {
        DefaultApiResponse<AuthorisationResponseDto> response = new DefaultApiResponse<>();
        log.info("Performing Authentication and Processing Login Request for USER with email: {}.", requestBody.email());
        try {
            // Validate the login request data
            LoginRequestDto.validate(requestBody);

            UserAccount userAccount;
            Optional<UserAccount> userOpt = userRepository.findByEmailAddress(requestBody.email());
            if(userOpt.isPresent()){
                userAccount = userOpt.get();

                log.info("USER Found on the DB with email {}.", requestBody.email());
                if(!passwordEncoder.matches(requestBody.password(), userAccount.getPassword())){
                    log.warn("Invalid Password for USER {}.", requestBody.email());
                    response.setStatusCode(LOGIN_INVALID_CREDENTIALS);
                    response.setStatusMessage("Invalid Password");
                    return response;
                }
            } else {
                log.warn("USER with email {} not found in the database.", requestBody.email());
                response.setStatusCode(STATUS_400);
                response.setStatusMessage("USER Not Found: OnBoard on the System or Verify Email");
                return response;
            }

            // Generate access and refresh tokens for the authenticated customer
            accessAndRefreshToken result = getGenerateAccessTokenAndRefreshToken(userAccount);

            AuthorisationResponseDto authorisationResponseDto = new AuthorisationResponseDto(
                    result.accessToken(), result.refreshToken(), getLastUpdatedAt(), "24hrs");

            // Authenticate the user with the provided credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestBody.email(), requestBody.password()));

            response.setStatusCode(LOGIN_SUCCESS);
            response.setStatusMessage("Successfully Logged In");
            response.setData(authorisationResponseDto);
            log.info("USER {} successfully logged in.", requestBody.email());

        } catch (RuntimeException ex){
            log.error("An error occurred while performing Authentication for USER {}: {}", requestBody.email(), ex.getMessage());
        }
        return response;
    }

    @Override
    public DefaultApiResponse<AuthorisationResponseDto> refreshToken(RefreshTokenRequestDto requestBody) {
        log.info("Processing Refreshing Token Request for user.");
        DefaultApiResponse<AuthorisationResponseDto> response = new DefaultApiResponse<>();

        try {
            String userEmail = jwtService.extractUsername(requestBody.refreshToken());
            log.info("Email of the Refresh Token: {}", userEmail);

            log.info("Checking if Refresh token has expired.");
            if(jwtService.isTokenExpired(requestBody.refreshToken())){
                response.setStatusCode(STATUS_400);
                response.setStatusMessage("Refresh Token Expired: User needs to Log in Again");
                log.warn("Refresh Token has expired for user {}: {}", userEmail, requestBody.refreshToken());
                return response;
            }

            Optional<UserAccount> existingUserAccount = userRepository.findByEmailAddress(userEmail);
            if(existingUserAccount.isPresent()){
                UserAccount user = existingUserAccount.get();

                log.info("Verifying Token is valid and properly signed for user {}.", userEmail);
                if(jwtService.isTokenValid(requestBody.refreshToken(), user)){
                    log.info("Generating New Token for user {}.", userEmail);

                    String newAccessToken = jwtService.createJWT(user);
                    String newRefreshToken = jwtService.generateRefreshToken(generateRefreshTokenClaims(user), user);

                    // Revoke old tokens and save the new tokens
                    revokeOldTokens(user);
                    saveUserAccountToken(user, newAccessToken, newRefreshToken);

                    response.setStatusCode(REFRESH_TOKEN_SUCCESS);
                    response.setStatusMessage("Successfully Refreshed AuthToken");
                    AuthorisationResponseDto responseDto = new AuthorisationResponseDto(
                            newAccessToken, newRefreshToken, getLastUpdatedAt() , "24hrs");
                    response.setData(responseDto);
                } else {
                    log.warn("Invalid Token signature for user {}.", userEmail);
                }
            }
        } catch (RuntimeException ex){
            log.error("An error occurred while refreshing the token: {}", ex.getMessage());
        }
        return response;
    }

    private String getLastUpdatedAt(){
        return LocalDateTime.now().toString().replace("T", " ").substring(0, 16);
    }

    private @NotNull accessAndRefreshToken getGenerateAccessTokenAndRefreshToken(UserAccount user){
        // Log the token generation process
        log.info("Generating Access Token and Refresh Token for USER");

        String jwtToken = jwtService.createJWT(user);
        String refreshToken = jwtService.generateRefreshToken(generateRefreshTokenClaims(user), user);

        saveUserAccountToken(user, jwtToken, refreshToken);
        return new accessAndRefreshToken(jwtToken, refreshToken);
    }

    private @NotNull HashMap<String, Object> generateRefreshTokenClaims(UserAccount user){
        // Log the process of generating refresh token claims
        log.info("Generating Refresh Token Claims");

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmailAddress());
        claims.put("userId", user.getId());
        return claims;
    }

    private void saveUserAccountToken(UserAccount userAccount, String jwtToken, String refreshToken){
        // Log the process of saving tokens
        log.info("Saving tokens for USER {}", userAccount.getEmailAddress());

        // Save the generated access and refresh tokens for the customer
        AuthToken token = AuthToken.builder()
                .user(userAccount)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);

        // Log successful token saving
        log.info("Saved Access and Refresh tokens for USER {}", userAccount.getEmailAddress());
    }

    private void revokeOldTokens(UserAccount userAccount){
        // Log the process of revoking old tokens
        log.info("Revoking old tokens for customer {}", userAccount.getEmailAddress());

        // Revoke all old tokens for the customer
        List<AuthToken> validTokens = tokenRepository.findAllByUser_id(userAccount.getId());
        if (validTokens.isEmpty()){
            log.info("No valid tokens found for customer {}.", userAccount.getEmailAddress());
            return;
        }
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validTokens);

        // Log successful token revocation
        log.info("Revoked old tokens for customer {}.", userAccount.getEmailAddress());
    }
}
