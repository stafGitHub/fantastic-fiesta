package ru.shift.common.network.responce;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record SendMessage(@JsonProperty("body") String body,
                          @JsonProperty("localDate") LocalDate localDate,
                          @JsonProperty("name") String name) implements ServerMessage {
}
