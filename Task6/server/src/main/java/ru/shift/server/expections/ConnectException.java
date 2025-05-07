package ru.shift.server.expections;

public class ConnectException extends RuntimeException {
    public ConnectException(String message) {
        super(message);
    }
}
