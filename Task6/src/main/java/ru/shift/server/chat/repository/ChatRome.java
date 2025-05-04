package ru.shift.server.chat.repository;

import lombok.extern.slf4j.Slf4j;
import ru.shift.server.chat.session.UserSession;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public enum ChatRome implements Rome {
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
    public UserSession getUser(String username) {
        return users.get(username);
    }

    @Override
    public ConcurrentHashMap<String, UserSession> getAllUsers() {
        return users;
    }
}
