package ru.shift.server.chat.session;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.message.output.ServerMessage;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public enum SessionManager implements Manager {
    INSTANCE;

    private final ConcurrentHashMap<String, UserSession> users = new ConcurrentHashMap<>();

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public void addUser(UserSession session) {
        users.put(session.getUserName(), session);
    }

    @Override
    public void removeUser(String username) {
        users.remove(username);
    }

    @Override
    public void broadcastMessage(ServerMessage message) throws MessageException {
        users.forEach((username, session) -> {
            try {
                if (session.getSocket().isConnected()) {
                    session.sendMessage(message);
                } else {
                    users.remove(username);
                    log.warn("Удаленна неиспользуемая сессия: {}", username);
                }
            } catch (ConnectException e) {
                users.remove(username);
                log.warn("Пользователь отключился во время отправки сообщения : {}", e.getMessage());
                throw new MessageException(e.getMessage());
            }
        });
    }

    @Override
    public ConcurrentHashMap<String, UserSession> getAllUsers() {
        return users;
    }
}
