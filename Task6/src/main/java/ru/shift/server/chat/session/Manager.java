package ru.shift.server.chat.session;

import ru.shift.common.protocol.message.output.ServerMessage;

import java.util.concurrent.ConcurrentHashMap;

public interface Manager {
    boolean userExists(String username);

    void addUser(UserSession userSession);

    void removeUser(String username);

    void broadcastMessage(ServerMessage message);

    ConcurrentHashMap<String, UserSession> getAllUsers();
}
