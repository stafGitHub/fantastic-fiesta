package ru.shift.server.chat.repository;

import ru.shift.server.chat.session.UserSession;

import java.util.concurrent.ConcurrentHashMap;

public interface Rome {
    boolean userExists(String username);
    void addUser(UserSession userSession);
    void removeUser(String username);
    UserSession getUser(String username);
    ConcurrentHashMap<String,UserSession> getAllUsers();
}
