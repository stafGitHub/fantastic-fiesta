package ru.shift.client.model.event;

import ru.shift.client.event.Event;
import ru.shift.common.protocol.message.output.ServerMessage;

public record Message(ServerMessage serverMessage) implements Event {
}
