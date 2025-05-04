package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;

import java.util.Calendar;

public record SendMessage(@JsonProperty("protocol") ApplicationProtocol applicationProtocol,
                          @JsonProperty("body") String body,
                          @JsonProperty("calendar") Calendar calendar) implements ServerMessage {
}
