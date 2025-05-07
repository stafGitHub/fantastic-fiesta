package ru.shift.server.chat.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.JsonException;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
@Getter
public class UserSession implements AutoCloseable {
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private String userName;
    private String sessionId;
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

    public ClientMessage getMessage() throws ConnectException, JsonException {
        try {
            return objectMapper.readValue(reader.readLine(), ClientMessage.class);
        } catch (JsonProcessingException e) {
            log.warn("Ошибка чтения json : {}", e.getMessage());
            throw new JsonException(e.getMessage());
        } catch (IOException e) {
            log.warn("Ошибка соединения: {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

    public void sendMessage(ServerMessage message) throws ConnectException {
        try {
            writer.println(objectMapper.writeValueAsString(message));
        } catch (IOException e) {
            log.warn("Ошибка отправки json : {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

    public void setUserName(String userName) {
        log.info("Имя : {}, установленно", userName);
        this.userName = userName;
    }

    public void setSessionId(String sessionId) {
        log.info("SessionId {}, установленна", sessionId);
        this.sessionId = sessionId;
    }
}