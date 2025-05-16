package ru.shift.server.chat.endpoints.specific.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.ChatMessage;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j

public class ChatMessageEndpoint extends AbstractEndpoint {

    public ChatMessageEndpoint(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected void processMessage(UserSession session, ClientMessage message) {
        log.info("Message received: {}", message.body());
        sessionManager.broadcastMessage(new ChatMessage(
                LocalDate.now(),
                message.body(),
                session.getUserName()
        ));
    }
}