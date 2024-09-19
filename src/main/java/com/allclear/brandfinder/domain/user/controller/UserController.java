package com.allclear.brandfinder.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allclear.brandfinder.domain.user.dto.JoinForm;
import com.allclear.brandfinder.domain.user.dto.LoginForm;
import com.allclear.brandfinder.domain.user.entity.User;
import com.allclear.brandfinder.domain.user.service.UserService;
import com.allclear.brandfinder.global.exception.CustomException;
import com.allclear.brandfinder.global.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public SuccessResponse<String> signUp(@Valid @RequestBody JoinForm form) {
        try {
            userService.signUp(form);
            return SuccessResponse.withNoData("회원가입이 완료되었습니다.");

        } catch (CustomException ex) {
            log.error("회원가입이 실패했습니다.");
            throw ex;
        }

    }

    @PostMapping("/login")
    public SuccessResponse<String> signIn(@Valid @RequestBody LoginForm form) {
        try {
            userService.signIn(form);
            return SuccessResponse.withNoData("로그인이 완료되었습니다.");

        } catch (CustomException ex) {
            log.error("로그인에 실패했습니다.");
            throw ex;
        }
    }

}
