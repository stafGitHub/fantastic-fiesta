package ru.shift.server.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.message.output.Message;
import ru.shift.server.chat.repository.Rome;
import ru.shift.server.expections.MessageException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class MessageSender {
    private final Rome rome;

    public void broadcastMessage(Message message) throws MessageException {
        rome.getAllUsers().forEach((username, session) -> {
            try {
                if (session.getSocket().isConnected()) {
                    session.sendMessage(message);
                } else {
                    rome.getAllUsers().remove(username);
                }
            } catch (IOException e) {
                rome.getAllUsers().remove(username);
                log.error(e.getMessage());
                throw new MessageException(e.getMessage());
            }
        });
    }
}
