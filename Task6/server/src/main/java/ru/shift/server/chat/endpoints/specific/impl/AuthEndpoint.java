package ru.shift.server.chat.endpoints.specific.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.LoginMessageError;
import ru.shift.network.model.LoginMessageSuccess;
import ru.shift.network.model.SystemMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;
import ru.shift.server.expections.UserAlreadyExists;

import java.time.LocalDate;

@Slf4j
public class AuthEndpoint extends AbstractEndpoint<LoginMessageSuccess> {

    public AuthEndpoint(MessageType messageType,
                        SessionManager sessionManager) {
        super(messageType, sessionManager);
    }

    @Override
    protected LoginMessageSuccess processMessage(UserSession session, ClientMessage message) throws MessageException {
        session.setUserName(message.body());

        try {
            sessionManager.addUser(session);
        } catch (UserAlreadyExists e) {
            sendMessage(session, new LoginMessageError(LocalDate.now(), e.getMessage()));
            throw new MessageException(e.getMessage());
        }

        log.info("{} подключился", session.getUserName());
        sessionManager.broadcastMessage(
                new SystemMessage(LocalDate.now(),
                        SystemMessageStatus.LOGIN,
                        session.getUserName())
        );

        return new LoginMessageSuccess(LocalDate.now());
    }

}
