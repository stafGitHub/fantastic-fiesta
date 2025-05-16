package ru.shift.server.chat.endpoints.specific.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.model.SystemMessageStatus;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.LoginMessageError;
import ru.shift.network.model.LoginMessageSuccess;
import ru.shift.network.model.SystemMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.UserAlreadyExists;

import java.time.LocalDate;

@Slf4j
public class AuthEndpoint extends AbstractEndpoint {

    public AuthEndpoint(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected void processMessage(UserSession session, ClientMessage message) throws ConnectException {
        session.setUserName(message.body());

        try {
            sessionManager.addUser(session);
        } catch (UserAlreadyExists e) {
            sendMessage(session, new LoginMessageError(LocalDate.now(), e.getMessage()));
            return;
        }

        sendMessage(session,new LoginMessageSuccess(LocalDate.now()));

        log.info("{} подключился", session.getUserName());
        sessionManager.broadcastMessage(
                new SystemMessage(LocalDate.now(),
                        SystemMessageStatus.LOGIN,
                        session.getUserName())
        );

    }

}
