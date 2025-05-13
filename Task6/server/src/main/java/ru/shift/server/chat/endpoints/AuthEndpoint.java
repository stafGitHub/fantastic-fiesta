package ru.shift.server.chat.endpoints;


import lombok.extern.slf4j.Slf4j;
import ru.shift.network.RequestType;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.network.model.LoginMessageError;
import ru.shift.network.model.LoginMessageSuccess;
import ru.shift.network.model.SystemMessage;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;

import java.util.UUID;

@Slf4j

public class AuthEndpoint implements Endpoint {
    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        if (!sessionManager.userExists(message.body())) {
            session.setUserName(message.body());
            session.setSessionId(session.getUserName() + UUID.randomUUID());


            sendMessage(session, new LoginMessageSuccess());


            sessionManager.addUser(session);

            log.info("{} подключился", session.getUserName());

            sessionManager.broadcastMessage(new SystemMessage(
                    SystemMessageStatus.LOGIN,
                    session.getUserName()));
        } else {
            sendMessage(session, new LoginMessageError());
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
    public RequestType getProtocol() {
        return RequestType.LOGIN;
    }
}
