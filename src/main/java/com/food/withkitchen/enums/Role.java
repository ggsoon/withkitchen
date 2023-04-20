package com.food.withkitchen.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), GUSET("ROLE_GUEST");

    private final String key;
}
