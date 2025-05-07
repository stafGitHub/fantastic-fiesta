package ru.shift.network.model;


import ru.shift.network.message.ServerMessage;

import java.util.List;

public record UsersMessage(List<String> users) implements ServerMessage {
}
