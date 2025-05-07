package ru.shift.common.network.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.common.network.message.ServerMessage;

import java.time.LocalDate;

public record SendMessage(@JsonProperty("body") String body,
                          @JsonProperty("localDate") LocalDate localDate,
                          @JsonProperty("name") String name) implements ServerMessage {
}
