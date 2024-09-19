package com.allclear.brandfinder.domain.auth.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.allclear.brandfinder.domain.auth.util.AccessTokenUtil;
import com.allclear.brandfinder.domain.auth.util.RefreshTokenUtil;
import com.allclear.brandfinder.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AccessTokenUtil accessTokenUtil;
    private final RefreshTokenUtil refreshTokenUtil;

    @Override
    public HttpHeaders createHttpHeaders(String username) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String accessToken = accessTokenUtil.createAccessToken(username);
        refreshTokenUtil.saveRefreshToken(username);

        httpHeaders.set("Authorization", accessToken);
        httpHeaders.set("RefreshTokenName", username);

        return httpHeaders;
    }

}
