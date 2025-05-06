package ru.shift.server.chat.session;

import ru.shift.common.network.responce.ServerMessage;

import java.util.List;

public interface Manager {
    boolean userExists(String username);

    void addUser(UserSession userSession);

    void removeUser(String username);

    void broadcastMessage(ServerMessage message);

    List<String> getAllUsers();
}
