package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.SendMessage;
import ru.shift.server.chat.session.Manager;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

import java.util.Calendar;

@Slf4j
public class ChatMessageEndpoint implements Endpoint {
    private static final Manager sessionManager = SessionManager.INSTANCE;

    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        log.info("Message received: {}", message.body());
        sessionManager.broadcastMessage(new SendMessage(
                ApplicationProtocol.SEND_MESSAGE,
                message.body(),
                Calendar.getInstance()
        ));
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.SEND_MESSAGE;
    }
}