package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

@Getter
public class SystemMessage extends ServerMessage {
    private final SystemMessageStatus messageStatus;
    private final String sender;

    @Builder
    @JsonCreator
    public SystemMessage(LocalDate dispatchDate, SystemMessageStatus messageStatus, String sender) {
        super(dispatchDate);
        this.messageStatus = messageStatus;
        this.sender = sender;
    }
}
