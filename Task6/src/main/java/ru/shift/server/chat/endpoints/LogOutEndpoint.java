package ru.shift.server.chat.endpoints;

import ru.shift.server.chat.repository.ChatRome;
import ru.shift.server.chat.session.UserSession;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.UserMessage;

public class LogOutEndpoint implements Endpoint {
    private final ChatRome chatRome = ChatRome.INSTANCE;

    @Override
    public void process(UserSession session, UserMessage message) {
        chatRome.removeUser(message.body());
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.LOGOUT;
    }
}
