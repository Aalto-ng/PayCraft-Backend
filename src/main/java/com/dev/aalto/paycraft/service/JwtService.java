package com.dev.aalto.paycraft.service;

import com.dev.aalto.paycraft.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    private static final long EXPIRATION_TIME = 86400000; //24hrs

    public void JWTService() {
        byte[] keyByte = Base64.getDecoder().decode(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyByte,"HmacSHA256");
    }

    public String createJWT(UserAccount user) { return  generateToken(user); }

    private String generateToken(UserAccount user){
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("userID",user.getUserId());
        claims.put("firstName",user.getFirstName());
        claims.put("lastName",user.getLastName());
        claims.put("email",user.getEmailAddress());
        claims.put("phoneNumber",user.getPhoneNumber());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserAccount user){
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
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