package ru.shift.server.chat.endpoints;


import ru.shift.network.exception.ConnectException;
import ru.shift.network.message.ClientMessage;
import ru.shift.server.chat.session.UserSession;

public interface Endpoint {
    void process(UserSession session, ClientMessage message) throws ConnectException;

}
