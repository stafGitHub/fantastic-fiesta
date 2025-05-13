package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;
import java.util.List;

@Getter
public class UsersMessage extends ServerMessage {
    private final List<String> users;

    @JsonCreator
    public UsersMessage(@JsonProperty("dispatchDate") LocalDate dispatchDate,
                        @JsonProperty("users") List<String> users) {
        super(dispatchDate);
        this.users = users;
    }
}
