package ru.shift.server.chat.endpoints.specific.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.ChatMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

import java.time.LocalDate;

@Slf4j

public class ChatMessageEndpoint extends AbstractEndpoint<ChatMessage> {

    public ChatMessageEndpoint(MessageType messageType,
                               SessionManager sessionManager) {
        super(messageType, sessionManager);
    }

    @Override
    protected ChatMessage processMessage(UserSession session, ClientMessage message) throws MessageException {
        log.info("Message received: {}", message.body());
        sessionManager.broadcastMessage(new ChatMessage(
                message.body(),
                LocalDate.now(),
                session.getUserName()
        ));

        return null;
    }
}