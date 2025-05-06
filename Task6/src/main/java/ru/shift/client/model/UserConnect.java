package ru.shift.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.event.Message;
import ru.shift.client.model.session.UserSession;
import ru.shift.common.protocol.message.ClientMessage;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public enum UserConnect implements Publisher {
    INSTANCE;

    private UserSession userSession;

    public void connect(String host, int port) throws IOException {
        try {
            userSession = new UserSession(new Socket(host, port));
            userSession.setServerAddress(host + port);
        } catch (IOException e) {
            log.info("Ошибка подключения : {}", e.getMessage(), e);
            throw e;
        }
        observerMessage();
    }

    private void observerMessage(){
        new Thread(() -> {
            while (true) {
                notifyListeners(new Message(userSession.getMessage()));
            }
        }).start();
    }

    public void sendMessage(ClientMessage message) {
        userSession.sendMessage(message);
    }

}

