package ru.shift.server.chat.session;

import lombok.extern.slf4j.Slf4j;
import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ServerMessage;
import ru.shift.server.expections.UserAlreadyExists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionManager implements Manager {
    private final ConcurrentHashMap<String, UserSession> users = new ConcurrentHashMap<>();

    @Override
    public synchronized void addUser(UserSession session) throws UserAlreadyExists {
        if (users.containsKey(session.getUserName())) {
            throw new UserAlreadyExists("Пользователь " + session.getUserName() + "уже существует");
        } else {
            users.put(session.getUserName(), session);
        }
    }

    @Override
    public void removeUser(String username) {
        var removedUser = users.remove(username);

        try {
            removedUser.getSocket().close();
        } catch (IOException e) {
            log.info("Не удалось закрыть соединение: {}", e.getMessage());
        }
    }

    @Override
    public void broadcastMessage(ServerMessage message) {
        users.forEach((username, session) -> {
            try {
                session.sendMessage(message);
            } catch (ConnectException e) {
                users.remove(username);
                log.warn("Удаленна неиспользуемая сессия: {}", username);
            }
        });
    }

    @Override
    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }
}
