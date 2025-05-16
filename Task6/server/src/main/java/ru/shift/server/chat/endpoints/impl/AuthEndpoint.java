package ru.shift.server.chat.endpoints.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.*;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.UserAlreadyExists;

import java.time.LocalDate;

@Slf4j
public class AuthEndpoint extends AbstractEndpoint {
    @Override
    protected void processMessage(UserSession session, ClientMessage message) throws ConnectException {
        session.setUserName(message.body());

        try {
            sessionManager.addUser(session);
        } catch (UserAlreadyExists e) {

            var loginMessageError = new LoginMessageError();
            loginMessageError.setDispatchDate(LocalDate.now());
            loginMessageError.setException(e.getMessage());

            sendMessage(session, loginMessageError);
            return;
        }

        var loginMessageSuccess = new LoginMessageSuccess();
        loginMessageSuccess.setDispatchDate(LocalDate.now());

        sendMessage(session, loginMessageSuccess);

        log.info("{} подключился", session.getUserName());

        var systemMessage = new SystemMessage();
        systemMessage.setDispatchDate(LocalDate.now());
        systemMessage.setMessageStatus(SystemMessageStatus.LOGIN);
        systemMessage.setSender(session.getUserName());

        sessionManager.broadcastMessage(systemMessage);

    }

    @Override
    public MessageType getMessageType() {
        return MessageType.LOGIN;
    }
}
