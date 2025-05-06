package ru.shift.common.network.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.network.ApplicationProtocol;

import java.io.Serializable;

public record ClientMessage(@JsonProperty("protocol") ApplicationProtocol protocol,
                            @JsonProperty("body") String body) implements Serializable {

}