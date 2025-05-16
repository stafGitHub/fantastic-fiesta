package ru.shift.network.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.exception.JsonException;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AbstractUserSession<R, W> implements AutoCloseable {
    private final Class<R> responseType;
    @Getter
    protected final Socket socket;
    protected final PrintWriter writer;
    protected final BufferedReader reader;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected AbstractUserSession(Socket socket, Class<R> responseType) throws ConnectException {
        this.responseType = responseType;
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

    public R readMessageFromTheInternet() throws ConnectException, JsonException {
        try {
            return objectMapper.readValue(reader.readLine(), responseType);
        } catch (JsonProcessingException e) {
            log.warn("Ошибка чтения json : {}", e.getMessage());
            throw new JsonException(e.getMessage());
        } catch (IOException e) {
            log.warn("Ошибка соединения: {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

    public void sendMessage(W message) throws ConnectException {
        try {
            writer.println(objectMapper.writeValueAsString(message));
        } catch (IOException e) {
            log.warn("Ошибка отправки json : {}", e.getMessage());
            throw new ConnectException(e.getMessage());
        }
    }

}