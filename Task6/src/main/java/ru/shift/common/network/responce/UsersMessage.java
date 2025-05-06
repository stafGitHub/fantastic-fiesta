package ru.shift.common.network.responce;

import java.util.List;

public record UsersMessage(List<String> users) implements ServerMessage {
}
