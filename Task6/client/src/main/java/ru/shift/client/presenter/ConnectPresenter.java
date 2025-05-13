package ru.shift.client.presenter;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.presenter.event.NextWindow;
import ru.shift.client.view.concrete.ConnectView;
import ru.shift.network.exception.ConnectException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConnectPresenter implements Presenter, Publisher {
    private final ConnectView connectView;
    private final List<Observer> observers = new ArrayList<>();

    public ConnectPresenter(ConnectView connectView) {
        this.connectView = connectView;
        connectView.addActionListener(this);
    }

    @Override
    public void onButtonClick() {
        var split = connectView.getServerAddress().split(":");
        log.info("Получены данные для подключения: {}", connectView.getServerAddress());
        try {
            userConnect.connect(split[0], Integer.parseInt(split[1]));
            connectView.setVisible(false);
            notifyListeners(new NextWindow());

        } catch (IOException | IndexOutOfBoundsException | ConnectException e) {
            log.warn("Ошибка подключения: {}", e.getMessage(), e);
            connectView.showError("Сервер не найден");
        }
    }

    @Override
    public void notifyListeners(Event event) {
        observers.forEach(observer -> observer.event(event));
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }
}
