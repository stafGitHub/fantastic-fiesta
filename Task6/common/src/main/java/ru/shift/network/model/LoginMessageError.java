package ru.shift.network.model;


import ru.shift.network.MessageType;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public record LoginMessageError() implements ServerMessage {
    @Override
    public MessageType getMessageStatus() {
        return MessageType.LOGIN_FAIL;
    }

    @Override
    public LocalDate getMessageDate() {
        return ServerMessage.super.getMessageDate();
    }
}
