package ru.shift.client.model.event;

import ru.shift.common.protocol.message.output.ServerMessage;

public record Login(ServerMessage serverMessage) implements Event {
}
