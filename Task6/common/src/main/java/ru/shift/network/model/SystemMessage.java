package ru.shift.network.model;

import lombok.Getter;
import lombok.Setter;
import ru.shift.network.message.ServerMessage;

@Getter
@Setter
public class SystemMessage extends ServerMessage {
    private SystemMessageStatus messageStatus;
    private String sender;
}
