package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;

@Getter
public class SystemMessage extends ServerMessage {
    private final SystemMessageStatus messageStatus;
    private final String sender;

    @JsonCreator
    public SystemMessage(@JsonProperty("dispatchDate") LocalDate dispatchDate,
                         @JsonProperty("messageStatus") SystemMessageStatus messageStatus,
                         @JsonProperty("sender") String sender) {
        super(dispatchDate);
        this.messageStatus = messageStatus;
        this.sender = sender;
    }
}
