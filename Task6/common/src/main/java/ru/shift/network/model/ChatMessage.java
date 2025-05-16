package ru.shift.network.model;

import lombok.Getter;
import lombok.Setter;
import ru.shift.network.message.ServerMessage;

@Getter
@Setter
public class ChatMessage extends ServerMessage {
    private String message;
    private String sender;
}
