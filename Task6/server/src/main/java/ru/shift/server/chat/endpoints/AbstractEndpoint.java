package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;

@Slf4j
public abstract class AbstractEndpoint<T extends ServerMessage> implements Endpoint {
    protected final SessionManager sessionManager;
    private final MessageType messageType;

    protected AbstractEndpoint(MessageType messageType,
                               SessionManager sessionManager) {
        this.messageType = messageType;
        this.sessionManager = sessionManager;
    }

    protected abstract T processMessage(UserSession session, ClientMessage message) throws MessageException;

    @Override
    public final void process(UserSession session, ClientMessage message) throws MessageException {
        try {
            log.info("Обработка сообщения {} , {}", messageType, message.body());

            T response = processMessage(session, message);

            if (response != null) {
                sendMessage(session, response);
            }
        } catch (MessageException e) {
            log.warn("Ошибка обработки сообщения от {}: {}", session.getUserName(), e.getMessage());
            throw e;
        }
    }

    protected final void sendMessage(UserSession session, ServerMessage message) throws MessageException {
        try {
            session.sendMessage(message);
        } catch (ConnectException e) {
            log.warn("Не удалось отправить {} для {}: {}", message.getClass(), session.getUserName(), e.getMessage());
            throw new MessageException(e.getMessage());
        }
    }

}
