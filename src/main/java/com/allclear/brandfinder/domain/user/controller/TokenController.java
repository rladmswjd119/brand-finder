package com.allclear.brandfinder.domain.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allclear.brandfinder.domain.auth.service.AuthService;
import com.allclear.brandfinder.domain.auth.util.RefreshTokenUtil;
import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final RefreshTokenUtil refreshTokenUtil;
    private final AuthService authService;

    @GetMapping("/refresh")
    public ResponseEntity<String> newToken(@RequestHeader("RefreshToken") String username) {
        log.info("{}의 Refresh Token을 확인합니다.", username);
        HttpHeaders headers = null;

        try {
            refreshTokenUtil.checkRefreshToken(username);

        } catch (CustomException ex) {
            headers = authService.createHttpHeaders(username);
        }

        if(headers == null) {
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("새로운 access token이 발급되었습니다");
    }
}
