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

            var loginMessageError = LoginMessageError.builder()
                    .dispatchDate(LocalDate.now())
                    .exception(e.getMessage())
                    .build();

            sendMessage(session, loginMessageError);
            return;
        }

        var loginMessageSuccess = LoginMessageSuccess.builder()
                .dispatchDate(LocalDate.now())
                .build();

        sendMessage(session, loginMessageSuccess);

        log.info("{} подключился", session.getUserName());

        var systemMessage = SystemMessage.builder()
                .dispatchDate(LocalDate.now())
                .messageStatus(SystemMessageStatus.LOGIN)
                .sender(session.getUserName())
                .build();

        sessionManager.broadcastMessage(systemMessage);

    }

    @Override
    public MessageType getMessageType() {
        return MessageType.LOGIN;
    }
}
