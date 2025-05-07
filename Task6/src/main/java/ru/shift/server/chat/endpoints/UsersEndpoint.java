package ru.shift.server.chat.endpoints;

import ru.shift.common.network.ApplicationProtocol;
import ru.shift.common.network.message.ClientMessage;
import ru.shift.common.network.server.UsersMessage;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

public class UsersEndpoint implements Endpoint {
    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        var allUsers = sessionManager.getAllUsers();
        session.sendMessage(new UsersMessage(allUsers));
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.GET_USERS;
    }
}
