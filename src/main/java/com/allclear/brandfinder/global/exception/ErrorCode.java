package com.allclear.brandfinder.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_USERNAME_FORMAT(HttpStatus.BAD_REQUEST, "계정명은 3자 이상 20자 이하로 설정해야 합니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상, 20자 이하의 영문자, 특수문자, 숫자가 포함되어야 합니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 사용중인 계정명 입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일 입니다.");


    private final HttpStatus status;
    private final String message;

}
