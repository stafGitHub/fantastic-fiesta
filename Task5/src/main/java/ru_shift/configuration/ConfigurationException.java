package ru_shift.configuration;

public class ConfigurationException extends RuntimeException {
    public static final String ERROR_MESSAGE = "Ошибка загрузки конфигурации";

    public ConfigurationException(Exception e) {
        super(ERROR_MESSAGE, e);
    }

    public ConfigurationException(String message) {
        super(message);
    }
}
