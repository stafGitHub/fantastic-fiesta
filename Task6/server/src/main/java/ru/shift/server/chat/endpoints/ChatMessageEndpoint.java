package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.ChatMessage;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j
public class ChatMessageEndpoint implements Endpoint {

    @Override
    public void process(UserSession session, ClientMessage message) {
        log.info("Message received: {}", message.body());
        sessionManager.broadcastMessage(new ChatMessage(
                message.body(),
                LocalDate.now(),
                session.getUserName()
        ));
    }

    @Override
    public MessageType getProtocol() {
        return MessageType.CHAT_MESSAGE;
    }
}