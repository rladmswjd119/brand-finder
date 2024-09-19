package com.allclear.brandfinder.domain.users.service;

import org.springframework.http.HttpHeaders;

import com.allclear.brandfinder.domain.users.dto.JoinForm;
import com.allclear.brandfinder.domain.users.dto.LoginForm;
import com.allclear.brandfinder.domain.users.entity.User;


public interface UserService {

    User signUp(JoinForm form);

    HttpHeaders signIn(LoginForm form);

}
