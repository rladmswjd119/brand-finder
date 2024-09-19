package com.allclear.brandfinder.domain.auth.util;

import java.security.SecureRandom;
import java.util.Date;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.exception.ErrorCode;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenUtil {

    private final AccessTokenUtil accessTokenUtil;
    private final RedisTemplate<String, String> redisTemplate;

    private final long EXPIRATION_TIME = (60*1000)*60*24*7;
    private final int KEY_LEN = 100000000;
    private final String BEARER = "Bearer ";

    public void saveRefreshToken(String username) {
        try {
            checkRefreshToken(username);
            String refreshToken = createRefreshToken();
            redisTemplate.opsForValue().set(username, refreshToken);

        } catch (CustomException ex) {
            log.error("Refresh Token이 존재합니다.");
        }
    }

    public void checkRefreshToken(String username) {
        String refreshToken = getRefreshToken(username);
        if(refreshToken != null) {
            log.error("Refresh Token이 이미 존재합니다 : {}", refreshToken);
            throw new CustomException(ErrorCode.DUPLICATE_REFRESH_TOKEN);
        }
    }

    private String getRefreshToken(String username) {
        String refreshToken = redisTemplate.opsForValue().get(username);

        log.info("Refresh Token : {}", refreshToken);
        return refreshToken;
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date endDate = new Date(now.getTime() + EXPIRATION_TIME);
        String payload = createSecureRandom(KEY_LEN);

        String refreshToken = BEARER + Jwts.builder()
                                            .claim("refreshToken", payload)
                                            .issuedAt(now)
                                            .expiration(endDate)
                                            .signWith(accessTokenUtil.getSecretJwtKey())
                                            .compact();

        log.info("Refresh token : {}", refreshToken);
        return refreshToken;
    }

    private String createSecureRandom(int limit) {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(secureRandom.nextInt(limit));

    }

}
