package ru.shift.server.chat.endpoints;


import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.UserMessage;
import ru.shift.common.protocol.message.output.ChatMessage;
import ru.shift.common.protocol.message.output.RegisterMessage;
import ru.shift.common.protocol.message.status.ProcessingStatus;
import ru.shift.server.chat.MessageSender;
import ru.shift.server.chat.repository.ChatRome;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

import java.io.IOException;
import java.util.UUID;

@Slf4j

public class AuthEndpoint implements Endpoint {

    private final ChatRome chatRome = ChatRome.INSTANCE;
    private final MessageSender messageSender = new MessageSender(chatRome);

    @Override
    public void process(UserSession session, UserMessage message) throws MessageException {
        if (!chatRome.userExists(message.body())) {
            session.setUserName(message.body());
            session.setSessionId(session.getUserName() + UUID.randomUUID());

            try {
                session.sendMessage(new RegisterMessage(ApplicationProtocol.LOGIN, ProcessingStatus.OK, session.getSessionId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            chatRome.addUser(session);
            log.info("{} подключился", message.body());
            messageSender.broadcastMessage(new ChatMessage(session.getUserName(),
                    "подключился",
                    ApplicationProtocol.SEND_MESSAGE)
            );
        } else {
            try {
                session.sendMessage(new RegisterMessage(ApplicationProtocol.LOGIN,ProcessingStatus.ERROR,null));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.LOGIN;
    }
}
