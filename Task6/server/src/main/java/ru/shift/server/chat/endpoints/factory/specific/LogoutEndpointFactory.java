package ru.shift.server.chat.endpoints.factory.specific;

import ru.shift.network.MessageType;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.endpoints.factory.EndpointsFactory;
import ru.shift.server.chat.endpoints.specific.impl.LogoutEndpoint;
import ru.shift.server.chat.session.SessionManager;

public class LogoutEndpointFactory implements EndpointsFactory<AbstractEndpoint<?>> {
    private static final MessageType MESSAGE_TYPE = MessageType.LOGOUT;

    @Override
    public MessageType getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public AbstractEndpoint<?> createEndpoint(SessionManager sessionManager) {
        return new LogoutEndpoint(MESSAGE_TYPE, sessionManager);
    }
}
