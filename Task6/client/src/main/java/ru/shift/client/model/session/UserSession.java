package ru.shift.client.model.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.exceptions.ConnectException;
import ru.shift.client.exceptions.JsonException;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
@Getter
public class UserSession implements AutoCloseable {
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    @Setter
    private String serverAddress;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserSession(Socket socket) throws ConnectException {
        try {
            this.socket = socket;
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            objectMapper.registerModule(new JavaTimeModule());

        } catch (IOException e) {
            throw new ConnectException(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
        writer.close();
        reader.close();
    }

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
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            log.debug("Отправка сообщения на сервер: {}", jsonMessage);
            writer.println(jsonMessage);
        } catch (IOException e) {
            log.warn("Ошибка отправки json : {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

}
