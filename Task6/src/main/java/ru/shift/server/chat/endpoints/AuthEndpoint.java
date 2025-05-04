package ru.shift.server.chat.endpoints;


import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.SystemMessageStatus;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.LoginMessageError;
import ru.shift.common.protocol.message.output.LoginMessageSuccess;
import ru.shift.common.protocol.message.output.ServerMessage;
import ru.shift.common.protocol.message.output.SystemMessage;
import ru.shift.server.chat.session.Manager;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;

import java.util.UUID;

@Slf4j

public class AuthEndpoint implements Endpoint {

    private static final Manager sessionManager = SessionManager.INSTANCE;

    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        if (!sessionManager.userExists(message.body())) {
            session.setUserName(message.body());
            session.setSessionId(session.getUserName() + UUID.randomUUID());


            sendMessage(session, new LoginMessageSuccess(ApplicationProtocol.LOGIN, session.getSessionId()));


            sessionManager.addUser(session);

            log.info("{} подключился", session.getUserName());

            sessionManager.broadcastMessage(new SystemMessage(
                    ApplicationProtocol.SEND_MESSAGE,
                    SystemMessageStatus.LOGIN,
                    session.getUserName()));
        } else {
            sendMessage(session, new LoginMessageError(ApplicationProtocol.LOGIN));
        }
    }

    private void sendMessage(UserSession session, ServerMessage message) throws MessageException {
        try {
            session.sendMessage(message);
        } catch (ConnectException e) {
            log.warn("Не удалось отправить {} : {}", message.getClass(), e.getMessage());
            throw new MessageException(e.getMessage());
        }
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.LOGIN;
    }
}
