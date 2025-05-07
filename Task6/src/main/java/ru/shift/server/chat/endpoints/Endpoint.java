package ru.shift.server.chat.endpoints;

import ru.shift.common.network.ApplicationProtocol;
import ru.shift.common.network.message.ClientMessage;
import ru.shift.server.chat.session.Manager;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

public interface Endpoint {
    Manager sessionManager = SessionManager.INSTANCE;

    void process(UserSession session, ClientMessage message) throws MessageException;

    ApplicationProtocol getProtocol();

}
