package ru.shift.client.model.session;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.network.session.AbstractUserSession;

import java.net.Socket;

@Slf4j
public class UserSession extends AbstractUserSession<ServerMessage, ClientMessage> {
    public UserSession(Socket socket, Class<ServerMessage> responseType) throws ConnectException {
        super(socket, responseType);
    }
}
