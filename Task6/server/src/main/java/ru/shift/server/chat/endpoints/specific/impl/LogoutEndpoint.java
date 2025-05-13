package ru.shift.server.chat.endpoints.specific.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.SystemMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j
public class LogoutEndpoint extends AbstractEndpoint<SystemMessage> {
    public LogoutEndpoint(MessageType messageType,
                          SessionManager sessionManager) {
        super(messageType, sessionManager);
    }

    @Override
    protected SystemMessage processMessage(UserSession session, ClientMessage message) {
        if (session.getUserName() != null) {
            sessionManager.removeUser(session.getUserName());
            log.info("Пользователь: {} - удалён", session.getUserName());

            sessionManager.broadcastMessage(
                    new SystemMessage(LocalDate.now(),
                            SystemMessageStatus.LOGOUT,
                            session.getUserName())
            );
        }

        return null;
    }


}
