package ru.shift.common.protocol;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SystemMessageStatus {
    LOGIN("Пользователь подключился"),
    LOGOUT("Пользователь отключился");

    private final String message;
}
