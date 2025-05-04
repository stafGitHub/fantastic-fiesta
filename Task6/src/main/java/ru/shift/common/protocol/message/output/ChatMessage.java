package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;

import java.io.Serializable;

public record ChatMessage(String sender,
                          String body,
                          ApplicationProtocol applicationProtocol) implements Message , Serializable {
    @JsonCreator
    public ChatMessage(
            @JsonProperty("sender") String sender,
            @JsonProperty("body") String body,
            @JsonProperty("protocol") ApplicationProtocol applicationProtocol
    ) {
        this.sender = sender;
        this.body = body;
        this.applicationProtocol = applicationProtocol;
    }
}
