package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.network.ApplicationProtocol;
import ru.shift.common.network.message.ClientMessage;
import ru.shift.common.network.server.SendMessage;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

import java.time.LocalDate;

@Slf4j
public class ChatMessageEndpoint implements Endpoint {

    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        log.info("Message received: {}", message.body());
        sessionManager.broadcastMessage(new SendMessage(
                message.body(),
                LocalDate.now(),
                session.getUserName()
        ));
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.SEND_MESSAGE;
    }
}