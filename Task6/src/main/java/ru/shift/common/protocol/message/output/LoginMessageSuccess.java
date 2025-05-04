package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;

public record LoginMessageSuccess(@JsonProperty("protocol") ApplicationProtocol applicationProtocol,
                                  @JsonProperty("session") String sessionId) implements ServerMessage {
}
