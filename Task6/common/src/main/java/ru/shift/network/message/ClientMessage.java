package ru.shift.network.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.ApplicationProtocol;

import java.io.Serializable;

public record ClientMessage(@JsonProperty("protocol") ApplicationProtocol protocol,
                            @JsonProperty("body") String body) implements Serializable {

}