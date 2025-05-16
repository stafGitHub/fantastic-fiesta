package ru.shift.server.chat.endpoints.factory.specific;

import ru.shift.network.model.MessageType;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.endpoints.factory.EndpointsFactory;
import ru.shift.server.chat.endpoints.specific.impl.ChatMessageEndpoint;
import ru.shift.server.chat.session.SessionManager;

public class ChatMessageEndpointFactory implements EndpointsFactory<ChatMessageEndpoint> {
    private static final MessageType MESSAGE_TYPE = MessageType.CHAT_MESSAGE;

    @Override
    public MessageType getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public ChatMessageEndpoint createEndpoint(SessionManager sessionManager) {
        return new ChatMessageEndpoint(sessionManager);
    }
}
