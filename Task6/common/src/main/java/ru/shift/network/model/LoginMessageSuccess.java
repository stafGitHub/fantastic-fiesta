package ru.shift.network.model;


import ru.shift.network.MessageType;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public record LoginMessageSuccess() implements ServerMessage {
    @Override
    public MessageType getMessageStatus() {
        return MessageType.LOGIN_SUCCESS;
    }

    @Override
    public LocalDate getMessageDate() {
        return ServerMessage.super.getMessageDate();
    }
}
