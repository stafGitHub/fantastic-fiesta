package ru.shift.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ServerMessage;

public record SystemMessage(@JsonProperty("messageStatus") SystemMessageStatus systemMessageStatus,
                            @JsonProperty("name") String name) implements ServerMessage {
}
