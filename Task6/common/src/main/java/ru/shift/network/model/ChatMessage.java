package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

@Getter
public class ChatMessage extends ServerMessage {
    private final String message;
    private final String sender;

    @JsonCreator
    public ChatMessage(@JsonProperty("dispatchDate") LocalDate dispatchDate,
                       @JsonProperty("message") String message,
                       @JsonProperty("sender") String sender) {
        super(dispatchDate);
        this.message = message;
        this.sender = sender;
    }
}
