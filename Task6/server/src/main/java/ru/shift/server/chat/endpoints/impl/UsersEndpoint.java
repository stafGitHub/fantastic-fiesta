package ru.shift.server.chat.endpoints.impl;


import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.MessageType;
import ru.shift.network.model.UsersMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j
public class UsersEndpoint extends AbstractEndpoint {
    @Override
    protected void processMessage(UserSession session, ClientMessage message) throws ConnectException {

        var usersMessage = new UsersMessage();
        usersMessage.setDispatchDate(LocalDate.now());
        usersMessage.setUsers(sessionManager.getAllUsers());

        sendMessage(session, usersMessage);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GET_USERS;
    }
}
