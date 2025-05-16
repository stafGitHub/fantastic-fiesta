package ru.shift.network.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.model.MessageType;

import java.io.Serializable;

public record ClientMessage(@JsonProperty() MessageType messageType,
                            @JsonProperty() String body) implements Serializable {
}