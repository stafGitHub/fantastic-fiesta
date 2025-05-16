package ru.shift.network.message;


import ru.shift.network.model.MessageType;

import java.io.Serializable;

public record ClientMessage(MessageType messageType,
                            String body) implements Serializable {
}