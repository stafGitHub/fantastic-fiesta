package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.MessageType;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public record SystemMessage(@JsonProperty("messageStatus") SystemMessageStatus systemMessageStatus,
                            @JsonProperty("departureDate") LocalDate departureDate,
                            @JsonProperty("sender") String sender) implements ServerMessage {

    public SystemMessage(SystemMessageStatus systemMessageStatus, String name) {
        this(systemMessageStatus, LocalDate.now(), name);
    }

    @Override
    public MessageType getMessageStatus() {
        return MessageType.SYSTEM_MESSAGE;
    }

    @Override
    public LocalDate getMessageDate() {
        return ServerMessage.super.getMessageDate();
    }
}
