package ru.shift.client.presenter;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.event.Message;
import ru.shift.client.presenter.event.NextWindow;
import ru.shift.client.view.concrete.ConnectNameView;
import ru.shift.network.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.model.LoginMessageError;
import ru.shift.network.model.LoginMessageSuccess;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConnectNamePresenter extends Observer implements Presenter, Publisher {
    private final ConnectNameView connectNameView;
    private final List<Observer> observers = new ArrayList<>();

    public ConnectNamePresenter(ConnectNameView connectNameView) {
        super(userConnect);
        this.connectNameView = connectNameView;
        connectNameView.addActionListener(this);
    }

    @Override
    public void onButtonClick() {
        var userName = connectNameView.getUserName();
        log.info("Попытка входа с именем пользователя: {}", userName);
        userConnect.sendMessage(new ClientMessage(MessageType.LOGIN, userName));
    }

    @Override
    public void event(Event event) {
        if (event instanceof Message serverMessage) {
            var message = serverMessage.serverMessage();

            if (message instanceof LoginMessageSuccess) {
                log.info("Успешный вход: {}", message);
                connectNameView.setVisible(false);
                userConnect.sendMessage(new ClientMessage(MessageType.GET_USERS, null));
                notifyListeners(new NextWindow());

            } else if (message instanceof LoginMessageError loginMessageError) {
                log.warn("Ошибка входа: {}", loginMessageError);
                connectNameView.showError(loginMessageError.getException());
            }
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
