package ru.shift.server.chat.session;


import ru.shift.network.message.ServerMessage;

import java.util.List;

public interface Manager {
    boolean userExists(String username);

    void addUser(UserSession userSession);

    void removeUser(String username);

    void broadcastMessage(ServerMessage message);

    List<String> getAllUsers();
}
