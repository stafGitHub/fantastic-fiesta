package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.MessageType;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public record LoginMessageError(@JsonProperty("exception") String exception) implements ServerMessage {
    @Override
    public MessageType getMessageStatus() {
        return MessageType.LOGIN_FAIL;
    }

    @Override
    public LocalDate getMessageDate() {
        return ServerMessage.super.getMessageDate();
    }
}
