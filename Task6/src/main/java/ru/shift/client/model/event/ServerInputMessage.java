package ru.shift.client.model.event;

import ru.shift.common.protocol.message.output.ServerMessage;

public record ServerInputMessage(ServerMessage serverMessage) implements ServerMessage {
}
