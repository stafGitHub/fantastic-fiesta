package ru.shift.server.expections;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }
}
