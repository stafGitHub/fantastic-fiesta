package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;

@Slf4j
public abstract class AbstractEndpoint<T extends ServerMessage> implements Endpoint {
    protected final SessionManager sessionManager;
    private final MessageType messageType;

    protected AbstractEndpoint(MessageType messageType,
                               SessionManager sessionManager) {
        this.messageType = messageType;
        this.sessionManager = sessionManager;
    }

    protected abstract T processMessage(UserSession session, ClientMessage message) throws ConnectException;

    @Override
    public final void process(UserSession session, ClientMessage message) throws ConnectException {
        log.info("Обработка сообщения {} , {}", messageType, message.body());
        try {
            T response = processMessage(session, message);
            if (response != null) {
                sendMessage(session, response);
            }
        } catch (ConnectException e) {
            log.info("Не удалось отправить сообщение: {}", e.getMessage(), e);
            throw e;
        }

    }

    protected final void sendMessage(UserSession session, ServerMessage message) throws ConnectException {
        session.sendMessage(message);
    }

}
