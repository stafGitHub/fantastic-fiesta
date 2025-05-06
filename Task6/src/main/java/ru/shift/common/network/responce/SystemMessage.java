package ru.shift.common.network.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.network.SystemMessageStatus;

public record SystemMessage(@JsonProperty("messageStatus") SystemMessageStatus systemMessageStatus,
                            @JsonProperty("name") String name) implements ServerMessage {
}
