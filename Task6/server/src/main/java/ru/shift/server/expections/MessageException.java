package ru.shift.server.expections;

public class MessageException extends RuntimeException {
    public MessageException(String message) {
        super(message);
    }
}
