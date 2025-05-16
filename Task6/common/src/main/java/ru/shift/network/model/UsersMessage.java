package ru.shift.network.model;


import lombok.Getter;
import lombok.Setter;
import ru.shift.network.message.ServerMessage;

import java.util.List;

@Getter
@Setter
public class UsersMessage extends ServerMessage {
    private List<String> users;
}
