package com.food.withkitchen.entity;

public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반사용자"),
    ADMIN("ROLE_ADMIN", "시스템관리자");

    private final String key;

    private final String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }
}
