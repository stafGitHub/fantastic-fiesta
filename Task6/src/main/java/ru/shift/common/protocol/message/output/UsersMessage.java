package ru.shift.common.protocol.message.output;

import java.util.List;

public record UsersMessage(List<String> users) implements ServerMessage {
}
