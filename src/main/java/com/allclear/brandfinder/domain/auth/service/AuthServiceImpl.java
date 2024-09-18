package com.allclear.brandfinder.domain.auth.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    @Override
    public HttpHeaders createHttpHeaders(String username) {

        return null;
    }

}
