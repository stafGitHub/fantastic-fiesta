package ru.shift.server.chat.endpoints.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.MessageType;
import ru.shift.network.model.SystemMessage;
import ru.shift.network.model.SystemMessageStatus;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j
public class LogoutEndpoint extends AbstractEndpoint {
    @Override
    protected void processMessage(UserSession session, ClientMessage message) {
        if (session.getUserName() != null) {
            sessionManager.removeUser(session.getUserName());
            log.info("Пользователь: {} - удалён", session.getUserName());

            var systemMessage = SystemMessage.builder()
                    .dispatchDate(LocalDate.now())
                    .messageStatus(SystemMessageStatus.LOGOUT)
                    .sender(session.getUserName())
                    .build();

            sessionManager.broadcastMessage(systemMessage);
        }
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.LOGOUT;
    }
}
