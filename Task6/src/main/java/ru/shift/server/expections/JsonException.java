package ru.shift.server.expections;

public class JsonException extends RuntimeException {
    public JsonException(String message) {
        super(message);
    }
}
