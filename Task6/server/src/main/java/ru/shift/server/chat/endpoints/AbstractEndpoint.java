package ru.shift.server.chat.endpoints;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.chat.session.SessionManager;
import ru.shift.server.chat.session.UserSession;

@Slf4j
public abstract class AbstractEndpoint implements Endpoint {
    /***
     * Менеджер сессий, устанавливается {@link EndpointsDispatcher}
     */
    protected SessionManager sessionManager ;

    protected abstract void processMessage(UserSession session, ClientMessage message) throws ConnectException;

    @Override
    public final void process(UserSession session, ClientMessage message) throws ConnectException {
        log.info("Обработка сообщения {} : {}", this.getClass().getSimpleName(), message.body());
        try {
            processMessage(session, message);
        } catch (ConnectException e) {
            log.info("Не удалось отправить сообщение: {}", e.getMessage(), e);
            throw e;
        }

    }

    /**
     *<strong>Важно:<strong> не вызывайте метод вручную, метод предназначен, для {@link EndpointsDispatcher}
     */
    @Deprecated
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    protected final void sendMessage(UserSession session, ServerMessage message) throws ConnectException {
        session.sendMessage(message);
    }
}
