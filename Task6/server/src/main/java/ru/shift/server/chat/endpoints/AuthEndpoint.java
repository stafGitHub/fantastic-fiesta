package ru.shift.server.chat.endpoints;


import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.SystemMessageStatus;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.network.model.LoginMessageError;
import ru.shift.network.model.LoginMessageSuccess;
import ru.shift.network.model.SystemMessage;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;
import ru.shift.server.expections.UserAlreadyExists;

@Slf4j

public class AuthEndpoint implements Endpoint {
    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        session.setUserName(message.body());

        try {
            sessionManager.addUser(session);
        } catch (UserAlreadyExists e) {
            sendMessage(session, new LoginMessageError(e.getMessage()));
            return;
        }

        sendMessage(session, new LoginMessageSuccess());

        log.info("{} подключился", session.getUserName());

        sessionManager.broadcastMessage(new SystemMessage(
                SystemMessageStatus.LOGIN,
                session.getUserName())
        );

    }

    private void sendMessage(UserSession session, ServerMessage message) throws MessageException {
        try {
            session.sendMessage(message);
        } catch (ConnectException e) {
            log.warn("Не удалось отправить {} : {}", message.getClass(), e.getMessage());
            throw new MessageException(e.getMessage());
        }
    }

    @Override
    public MessageType getProtocol() {
        return MessageType.LOGIN;
    }
}
