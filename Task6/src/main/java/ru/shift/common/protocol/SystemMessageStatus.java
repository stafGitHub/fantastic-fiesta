package ru.shift.common.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SystemMessageStatus {
    LOGIN("Пользователь подключился: "),
    LOGOUT("Пользователь отключился: ");

    private final String message;
}
