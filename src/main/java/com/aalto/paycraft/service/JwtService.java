package com.aalto.paycraft.service;

import com.aalto.paycraft.entity.EmployerProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Service for generating accessTokens and refreshTokens.
 * */
@Service @Slf4j
public class JwtService {

    @Value("${secret-string}")
    private String SECRET_STRING;
    private SecretKey SECRET_KEY;
    private static final long ACCESS_TOKEN_VALIDITY_TIME = 3_600_000; // 1hr
    private static final long REFRESH_TOKEN_VALIDITY_TIME = 86_400_000; //24hrs

    @PostConstruct
    public void init() {
        if (SECRET_STRING == null) {
            throw new IllegalStateException("SECRET_STRING is not configured");
        }
        byte[] keyByte = Base64.getDecoder().decode(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyByte, "HmacSHA256");
    }

    public String createJWT(EmployerProfile employerProfile) { return  generateToken(employerProfile); }

    private String generateToken(EmployerProfile employerProfile){
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("userID",employerProfile.getEmployerProfileId());
        claims.put("firstName",employerProfile.getFirstName());
        claims.put("lastName",employerProfile.getLastName());
        claims.put("email",employerProfile.getEmailAddress());
        claims.put("phoneNumber",employerProfile.getPhoneNumber());

        return Jwts.builder()
                .claims(claims)
                .subject(employerProfile.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, EmployerProfile employerProfile){
        return Jwts.builder()
                .claims(claims)
                .subject(employerProfile.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload());
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}