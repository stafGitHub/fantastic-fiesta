package ru.shift.server.chat.endpoints;


import ru.shift.network.message.ClientMessage;
import ru.shift.server.chat.session.UserSession;
import ru.shift.server.expections.MessageException;

public interface Endpoint {
    void process(UserSession session, ClientMessage message) throws MessageException;

}
