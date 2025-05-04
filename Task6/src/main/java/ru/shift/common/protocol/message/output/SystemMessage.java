package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.SystemMessageStatus;

public record SystemMessage(@JsonProperty("protocol") ApplicationProtocol applicationProtocol,
                            @JsonProperty("messageStatus") SystemMessageStatus systemMessageStatus,
                            @JsonProperty("name") String name) implements ServerMessage {
}
