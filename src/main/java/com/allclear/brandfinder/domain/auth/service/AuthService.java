package com.allclear.brandfinder.domain.auth.service;

import org.springframework.http.HttpHeaders;

public interface AuthService {

    HttpHeaders createHttpHeaders(String username);

}
