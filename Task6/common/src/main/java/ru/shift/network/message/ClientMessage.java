package ru.shift.network.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.MessageType;

import java.io.Serializable;

public record ClientMessage(@JsonProperty("messageType") MessageType messageType,
                            @JsonProperty("message") String body) implements Serializable ,Message {
}