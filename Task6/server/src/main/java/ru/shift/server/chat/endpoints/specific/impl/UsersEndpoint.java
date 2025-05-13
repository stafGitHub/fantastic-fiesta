package ru.shift.server.chat.endpoints.specific.impl;


import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.UsersMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

@Slf4j
public class UsersEndpoint extends AbstractEndpoint<UsersMessage> {
    public UsersEndpoint(MessageType messageType,
                         SessionManager sessionManager) {
        super(messageType, sessionManager);
    }

    @Override
    protected UsersMessage processMessage(UserSession session, ClientMessage message) throws MessageException {
        return new UsersMessage(sessionManager.getAllUsers());
    }

}
