package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.shift.network.MessageType;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;
import java.util.List;

public record UsersMessage(@JsonProperty("users") List<String> users,
                           @JsonProperty("departureDate")LocalDate departureDate) implements ServerMessage {
    public UsersMessage(List<String> users) {
        this(users, LocalDate.now());
    }

    @Override
    public MessageType getMessageStatus() {
        return MessageType.GET_USERS;
    }
}
