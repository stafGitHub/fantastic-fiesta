package ru.shift.client.model.session;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@Slf4j
@Getter
@Setter
public class UserSession extends AbstractUserSession<ServerMessage, ClientMessage> {
    private String serverAddress;

    public UserSession(Socket socket, Class<ServerMessage> responseType) throws ConnectException {
        super(socket, responseType);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public ServerMessage getMessage() throws ConnectException, JsonException {
        try {
            String rawMessage = reader.readLine();

            log.debug("Получено сообщение от сервера: {}", rawMessage);
            return objectMapper.readValue(rawMessage, ServerMessage.class);
        } catch (JsonProcessingException e) {
            log.warn("Ошибка чтения json : {}", e.getMessage());
            throw new JsonException(e.getMessage());
        } catch (IOException e) {

            if (socket.isClosed()) {
                return null;
            }

            log.warn("Ошибка соединения: {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

    public void sendMessage(ClientMessage message) throws ConnectException {
        super.sendMessage(message);
    }

    @Override
    public Socket getSocket() {
        return super.getSocket();
    }
}
