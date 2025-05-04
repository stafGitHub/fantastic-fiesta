package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;

public record LoginMessageError(@JsonProperty("protocol") ApplicationProtocol applicationProtocol)
        implements ServerMessage {
}
