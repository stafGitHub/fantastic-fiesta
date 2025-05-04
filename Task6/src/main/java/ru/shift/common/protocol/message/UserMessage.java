package ru.shift.common.protocol.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;

import java.io.Serializable;

public record UserMessage(ApplicationProtocol protocol, String body) implements Serializable {
    @JsonCreator
    public UserMessage(
            @JsonProperty("protocol") ApplicationProtocol protocol,
            @JsonProperty("body") String body
    ) {
        this.protocol = protocol;
        this.body = body;
    }
}