package com.allclear.brandfinder.domain.auth.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.allclear.brandfinder.domain.auth.service.UserDetailsServiceImpl;
import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessTokenUtil {

    @Value("${JWT_KEY}")
    private String JWT_KEY;
    private final long EXPIRATION_TIME  = 60*1000*30;
    private final String BEARER = "Bearer ";
    private final String AUTHORIZATION = "Authorization";

    private final UserDetailsServiceImpl userDetailsService;

    public String createAccessToken(String username) {
        Date now = new Date();
        Date endDate = new Date(now.getTime() + EXPIRATION_TIME);

        return BEARER + Jwts.builder()
                            .claim("username", username)
                            .issuedAt(now)
                            .expiration(endDate)
                            .signWith(getSecretJwtKey())
                            .compact();

    }

    public SecretKey getSecretJwtKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION);
        log.info("bearer token = {}", bearerToken);

        if(bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }

        return null;

    }

    public Authentication getAuthentication(String token) {
        String username = decodeUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("username = {}, password = {}", userDetails.getUsername(), userDetails.getPassword());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String decodeUsername(String token) {

        return String.valueOf(getClaims(token).get("username"));
    }

    private Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .verifyWith(getSecretJwtKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            log.info("claims : {}", claims);

        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        }

        log.info("Claims = {}", claims);
        return claims;
    }

}
