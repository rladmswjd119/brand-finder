package com.allclear.brandfinder.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RankEnum {
    GENERAL("일반"),
    SILVER("실버"),
    VIP("vip");

    private final String value;
}
