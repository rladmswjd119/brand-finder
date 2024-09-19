package com.allclear.brandfinder.domain.user.service;

import org.springframework.http.HttpHeaders;

import com.allclear.brandfinder.domain.user.dto.JoinForm;
import com.allclear.brandfinder.domain.user.dto.LoginForm;
import com.allclear.brandfinder.domain.user.entity.User;


public interface UserService {

    User signUp(JoinForm form);

    HttpHeaders signIn(LoginForm form);

}
