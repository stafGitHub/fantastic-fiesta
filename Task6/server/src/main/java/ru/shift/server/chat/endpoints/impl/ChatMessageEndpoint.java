package ru.shift.server.chat.endpoints.impl;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.ChatMessage;
import ru.shift.network.model.MessageType;
import ru.shift.server.chat.endpoints.AbstractEndpoint;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j
public class ChatMessageEndpoint extends AbstractEndpoint {
    @Override
    protected void processMessage(UserSession session, ClientMessage message) {
        log.info("Message received: {}", message.body());

        var chatMessage = ChatMessage.builder()
                .message(message.body())
                .sender(session.getUserName())
                .dispatchDate(LocalDate.now())
                .build();

        sessionManager.broadcastMessage(chatMessage);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CHAT_MESSAGE;
    }
}