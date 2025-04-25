package ru_shift.configuration;

public class ErrorConfiguration extends RuntimeException {
    public static final String ERROR_MESSAGE = "Ошибка загрузки конфигурации";

    public ErrorConfiguration(Exception e) {
        super(ERROR_MESSAGE, e);
    }

    public ErrorConfiguration(String message) {
        super(message);
    }
}
