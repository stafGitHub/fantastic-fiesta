package ru.shift.server.chat.session;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.exception.JsonException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.network.session.AbstractUserSession;

import java.io.IOException;
import java.net.Socket;
@Getter
@Setter
@Slf4j
public class UserSession extends AbstractUserSession<ClientMessage,ServerMessage> {
    private String userName;

    @Override
    public Socket getSocket() {
        return super.getSocket();
    }

    public UserSession(Socket socket, Class<ClientMessage> responseType) throws ConnectException {
        super(socket, responseType);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public ClientMessage getMessage() throws ConnectException, JsonException {
        return super.getMessage();
    }

    @Override
    public void sendMessage(ServerMessage message) throws ConnectException {
        super.sendMessage(message);
    }
}