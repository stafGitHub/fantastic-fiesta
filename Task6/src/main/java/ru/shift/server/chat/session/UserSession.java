package ru.shift.server.chat.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import ru.shift.common.protocol.message.UserMessage;
import ru.shift.common.protocol.message.output.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Getter
public class UserSession implements AutoCloseable {
    private final Socket socket;
    private final DataOutputStream outputStream;
    private final DataInputStream inputStream;
    @Setter
    private String userName;
    @Setter
    private String sessionId;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserSession(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void close() throws Exception {
        socket.close();
        outputStream.close();
        inputStream.close();
    }

    public UserMessage getMessage() throws IOException {
        return objectMapper.readValue(inputStream.readUTF(), UserMessage.class);
    }

    public void sendMessage(Message message) throws IOException {
        outputStream.writeUTF(objectMapper.writeValueAsString(message));
    }

}