package com.allclear.brandfinder.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 사용자
    INVALID_USERNAME_FORMAT(HttpStatus.BAD_REQUEST, "계정명은 3자 이상 20자 이하로 설정해야 합니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상, 20자 이하의 영문자, 특수문자, 숫자가 포함되어야 합니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 사용중인 계정명 입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일 입니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "가입된 계정이 아닙니다."),
    INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // JWT
    DUPLICATE_REFRESH_TOKEN(HttpStatus.CONFLICT, "Refresh Token이 이미 존재합니다"),
    AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, "재로그인이 필요합니다."),

    // 브랜드
    BRAND_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 브랜드는 존재하지 않습니다."),

    // 등급
    RANK_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 등급은 존재하지 않습니다.");


    private final HttpStatus status;
    private final String message;

}
