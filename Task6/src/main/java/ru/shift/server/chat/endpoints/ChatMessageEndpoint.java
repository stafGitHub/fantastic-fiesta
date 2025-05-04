package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.UserMessage;
import ru.shift.common.protocol.message.output.ServerMessage;
import ru.shift.common.protocol.message.status.ProcessingStatus;
import ru.shift.server.chat.MessageSender;
import ru.shift.server.chat.repository.ChatRome;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

import java.io.IOException;

@Slf4j
public class ChatMessageEndpoint implements Endpoint {
    private final ChatRome chatRome = ChatRome.INSTANCE;
    private final MessageSender messageSender = new MessageSender(chatRome);

    @Override
    public void process(UserSession session, UserMessage message) throws MessageException {
        log.info("Message received: {}", message.body());
        messageSender.broadcastMessage(new UserMessage(
                ApplicationProtocol.SEND_MESSAGE,
                message.body()
        ));

        try {
            session.sendStatusMessage(new ServerMessage(ProcessingStatus.OK));
        } catch (IOException e) {

            try {
                session.sendStatusMessage(new ServerMessage(ProcessingStatus.ERROR));
            } catch (IOException ex) {
                throw new MessageException(ex.getMessage());
            }

            throw new MessageException(e.getMessage());
        }
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.SEND_MESSAGE;
    }
}