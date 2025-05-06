package ru.shift.server.chat.endpoints;

import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.SystemMessageStatus;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.SystemMessage;
import ru.shift.server.chat.session.UserSession;

public class LogOutEndpoint implements Endpoint {
    @Override
    public void process(UserSession session, ClientMessage message) {
        sessionManager.removeUser(session.getUserName());
        sessionManager.broadcastMessage(new SystemMessage(ApplicationProtocol.LOGOUT, SystemMessageStatus.LOGOUT,session.getUserName()));
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.LOGOUT;
    }
}
