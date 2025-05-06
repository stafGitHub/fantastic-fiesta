package ru.shift.common.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SystemMessageStatus {
    LOGIN("Пользователь подключился: "),
    LOGOUT("Пользователь отключился: ");

    private final String message;
}
