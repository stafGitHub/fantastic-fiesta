package ru.shift.client.model.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.ServerMessage;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.JsonException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
@Getter
public class UserSession implements AutoCloseable {
    private final Socket socket;
    private final DataOutputStream outputStream;
    private final DataInputStream inputStream;
    @Setter
    private String serverAddress;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserSession(Socket socket) throws ConnectException {
        try {
            this.socket = socket;
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        }catch (IOException e) {
            throw new ConnectException(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
        outputStream.close();
        inputStream.close();
    }

    public ServerMessage getMessage() throws ConnectException , JsonException {
        try {
            return objectMapper.readValue(inputStream.readUTF(), ServerMessage.class);
        }catch (JsonProcessingException e) {
            log.warn("Ошибка чтения json : {}", e.getMessage());
            throw new JsonException(e.getMessage());
        }catch (IOException e) {
            log.warn("Ошибка соединения: {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

    public void sendMessage(ClientMessage message) throws ConnectException {
        try {
            outputStream.writeUTF(objectMapper.writeValueAsString(message));
        }catch (IOException e) {
            log.warn("Ошибка отправки json : {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

}