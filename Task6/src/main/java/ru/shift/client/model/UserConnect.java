package ru.shift.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.event.Message;
import ru.shift.client.model.session.UserSession;
import ru.shift.common.network.message.ClientMessage;
import ru.shift.server.expections.JsonException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public enum UserConnect implements Publisher {
    INSTANCE;

    private UserSession userSession;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final List<Observer> observers = new ArrayList<>();

    public void connect(String host, int port) throws IOException {
        log.info("Попытка подключения к серверу {}:{}", host, port);
        try {
            userSession = new UserSession(new Socket(host, port));
            userSession.setServerAddress(host + port);
        } catch (IOException e) {
            log.error("Ошибка подключения: {}", e.getMessage(), e);
            throw e;
        }
        observerMessage();
    }

    private void observerMessage() {
        executor.submit(() -> {
            while (!userSession.getSocket().isClosed()) {
                try {
                    var message = userSession.getMessage();
                    log.info("Обработка сообщения: {}", message);
                    notifyListeners(new Message(message));
                } catch (JsonException e) {
                    log.error("Ошибка обработки сообщения: {}", e.getMessage(), e);
                }
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
        try {
            userSession.close();
        } catch (IOException e) {
            log.error("Не удалось закрыть ресурсы : {}",e.getMessage(), e);
        }
    }

    public void sendMessage(ClientMessage message) {
        log.info("Отправка сообщения на сервер: {}", message);
        userSession.sendMessage(message);
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyListeners(Event event) {
        observers.forEach(observer -> observer.event(event));
    }
}
