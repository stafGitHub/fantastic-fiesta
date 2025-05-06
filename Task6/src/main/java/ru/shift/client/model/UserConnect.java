package ru.shift.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.event.Message;
import ru.shift.client.model.session.UserSession;
import ru.shift.common.network.request.ClientMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public enum UserConnect implements Publisher {
    INSTANCE;

    private UserSession userSession;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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

    private void observerMessage() {
        executor.submit(()->{
            while (!userSession.getSocket().isClosed()) {
                notifyListeners(new Message(userSession.getMessage()));
            }
        });
    }

    public void shutdown() {
        executor.shutdownNow();
        try {
            userSession.close();
        } catch (IOException e) {
            log.error("Не удалось закрыть ресурсы : {}",e.getMessage(), e);
        }
    }

    public void sendMessage(ClientMessage message) {
        userSession.sendMessage(message);
    }

}

