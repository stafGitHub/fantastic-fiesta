package ru.shift.server.chat.session;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.expections.ConnectException;
import ru.shift.server.expections.UserAlreadyExists;

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
    public void addUser(UserSession session) throws UserAlreadyExists {
        var userSession = users.putIfAbsent(session.getUserName(), session);
        if (userSession != null) {
            throw new UserAlreadyExists("Пользователь " + session.getUserName() + "уже существует");
        }
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
