package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

@Getter
public class ChatMessage extends ServerMessage {
    private final String message;
    private final String sender;

    @Builder
    @JsonCreator
    public ChatMessage(LocalDate dispatchDate, String message, String sender) {
        super(dispatchDate);
        this.message = message;
        this.sender = sender;
    }
}
