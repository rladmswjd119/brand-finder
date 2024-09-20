package com.allclear.brandfinder.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserInfoPattern {
    EMAIL_PATTERN("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\\.[a-zA-Z]{2,}$"),
    PASSWORD_PATTERN("^[a-zA-Z0-9~!@#$%^&*-_?]$");

    private final String pattern;
}
