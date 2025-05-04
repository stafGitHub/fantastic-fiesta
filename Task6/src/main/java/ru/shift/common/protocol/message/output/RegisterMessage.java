package ru.shift.common.protocol.message.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.status.ProcessingStatus;

import java.io.Serializable;

public record RegisterMessage(ApplicationProtocol applicationProtocol,
                              ProcessingStatus processingStatus,
                              String sessionUID) implements Message, Serializable {
    @JsonCreator
    public RegisterMessage(
            @JsonProperty("protocol") ApplicationProtocol applicationProtocol,
            @JsonProperty("status") ProcessingStatus processingStatus,
            @JsonProperty("body") String sessionUID
    ) {
        this.applicationProtocol = applicationProtocol;
        this.processingStatus = processingStatus;
        this.sessionUID = sessionUID;
    }
}
