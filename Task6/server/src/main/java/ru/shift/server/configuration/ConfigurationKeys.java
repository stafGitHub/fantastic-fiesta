package ru.shift.server.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfigurationKeys {
    PORT("port");
    private final String key;
}
