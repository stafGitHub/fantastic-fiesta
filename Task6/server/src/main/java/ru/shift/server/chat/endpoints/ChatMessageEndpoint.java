package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.RequestType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.SendMessage;
import ru.shift.server.chat.session.UserSession;

import java.time.LocalDate;

@Slf4j
public class ChatMessageEndpoint implements Endpoint {

    @Override
    public void process(UserSession session, ClientMessage message) {
        log.info("Message received: {}", message.body());
        sessionManager.broadcastMessage(new SendMessage(
                message.body(),
                LocalDate.now(),
                session.getUserName()
        ));
    }

    @Override
    public RequestType getProtocol() {
        return RequestType.SEND_MESSAGE;
    }
}