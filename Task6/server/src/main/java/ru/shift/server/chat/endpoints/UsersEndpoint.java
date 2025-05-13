package ru.shift.server.chat.endpoints;


import lombok.extern.slf4j.Slf4j;
import ru.shift.network.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.UsersMessage;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;

@Slf4j
public class UsersEndpoint implements Endpoint {
    @Override
    public void process(UserSession session, ClientMessage message) throws MessageException {
        var allUsers = sessionManager.getAllUsers();

        try {
            session.sendMessage(new UsersMessage(allUsers));
        }catch (ConnectException e){
            log.warn("Ошибка отправки сообщение: {}", e.getMessage(),e);
            throw new MessageException(e.getMessage());
        }
    }

    @Override
    public MessageType getProtocol() {
        return MessageType.GET_USERS;
    }
}
