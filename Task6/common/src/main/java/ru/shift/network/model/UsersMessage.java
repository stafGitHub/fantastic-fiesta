package ru.shift.network.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import ru.shift.network.message.ServerMessage;

import java.time.LocalDate;
import java.util.List;

@Getter
public class UsersMessage extends ServerMessage {
    private final List<String> users;

    @Builder
    @JsonCreator
    public UsersMessage(LocalDate dispatchDate, List<String> users) {
        super(dispatchDate);
        this.users = users;
    }
}
