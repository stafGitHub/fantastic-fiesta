package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.MessageType;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

public record ChatMessage(@JsonProperty("message") String message,
                          @JsonProperty("departureDate") LocalDate departureDate,
                          @JsonProperty("sender") String sender) implements ServerMessage {

    public ChatMessage(String body, String sender) {
        this(body, LocalDate.now(), sender);
    }

    @Override
    public MessageType getMessageStatus() {
        return MessageType.CHAT_MESSAGE;
    }

    @Override
    public LocalDate getMessageDate() {
        return departureDate;
    }
}
