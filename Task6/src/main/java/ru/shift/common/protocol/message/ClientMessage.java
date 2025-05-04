package ru.shift.common.protocol.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;

import java.io.Serializable;
import java.util.Calendar;

public record ClientMessage(@JsonProperty("protocol") ApplicationProtocol protocol,
                            @JsonProperty("body") String body,
                            @JsonProperty("data") Calendar calendar) implements Serializable {

}