package org.example.authentication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("admin::write"),
    ADMIN_READ("admin::read"),
    ADMIN_UPDATE("admin::update"),
    ADMIN_DELETE("admin::delete"),

    MANAGER_CREATE("management::write"),
    MANAGER_READ("management::read"),
    MANAGER_UPDATE("management::update"),
    MANAGER_DELETE("management::delete");

    private final String permission;
}

