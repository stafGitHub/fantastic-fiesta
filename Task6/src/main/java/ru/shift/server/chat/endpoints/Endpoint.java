package ru.shift.server.chat.endpoints;

import ru.shift.server.chat.session.UserSession;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.UserMessage;

import java.io.IOException;

public interface Endpoint {
    void process(UserSession session , UserMessage message) throws IOException;

    ApplicationProtocol getProtocol();

}
