package ru.shift.server.chat.session;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.MessageException;

import java.util.ArrayList;
import java.util.List;
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
    public void broadcastMessage(ServerMessage message) {
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
            }
        });
    }

    @Override
    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }


}
