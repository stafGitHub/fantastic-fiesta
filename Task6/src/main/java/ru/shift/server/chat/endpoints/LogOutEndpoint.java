package ru.shift.server.chat.endpoints;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.network.ApplicationProtocol;
import ru.shift.common.network.SystemMessageStatus;
import ru.shift.common.network.request.ClientMessage;
import ru.shift.common.network.responce.SystemMessage;
import ru.shift.server.chat.session.UserSession;

import java.io.IOException;

@Slf4j
public class LogOutEndpoint implements Endpoint {
    @Override
    public void process(UserSession session, ClientMessage message) {
        if (session.getUserName()!=null) {
            sessionManager.removeUser(session.getUserName());
            log.info("Пользователь: {} - удалён", session.getUserName());
            sessionManager.broadcastMessage(new SystemMessage(SystemMessageStatus.LOGOUT, session.getUserName()));
        }

        try {
            session.getSocket().close();
        } catch (IOException e) {
            log.info("Не удалось закрыть соединение: {}", e.getMessage());
        }
    }

    @Override
    public ApplicationProtocol getProtocol() {
        return ApplicationProtocol.LOGOUT;
    }
}
