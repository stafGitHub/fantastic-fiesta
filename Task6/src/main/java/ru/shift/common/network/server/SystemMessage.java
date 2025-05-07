package ru.shift.common.network.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.network.SystemMessageStatus;
import ru.shift.common.network.message.ServerMessage;

public record SystemMessage(@JsonProperty("messageStatus") SystemMessageStatus systemMessageStatus,
                            @JsonProperty("name") String name) implements ServerMessage {
}
