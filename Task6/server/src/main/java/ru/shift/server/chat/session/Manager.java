package ru.shift.server.chat.session;


import ru.shift.network.message.ServerMessage;
import ru.shift.server.expections.UserAlreadyExists;

import java.util.List;

public interface Manager {
    boolean userExists(String username);

    void addUser(UserSession userSession) throws UserAlreadyExists;

    void removeUser(String username);

    void broadcastMessage(ServerMessage message);

    List<String> getAllUsers();
}
