package ru.shift.common.network.server;

import ru.shift.common.network.message.ServerMessage;

import java.util.List;

public record UsersMessage(List<String> users) implements ServerMessage {
}
