package ru.shift.client.presenter;

import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.HttpClient;
import ru.shift.client.model.event.Connect;
import ru.shift.client.model.event.Event;
import ru.shift.client.view.concrete.ConnectNameView;

public class ConnectNamePresenter extends Observer implements Presenter {
    private final ConnectNameView view;
    private final HttpClient client;

    public ConnectNamePresenter(ConnectNameView view, HttpClient client, Publisher... publishers) {
        super(publishers);
        this.view = view;
        this.client = client;
        view.addActionListener(this);
    }

    @Override
    public void serverEvent(Event event) {
        if (event instanceof Connect){
            view.setVisible(true);
        }
    }

    @Override
    public void onMouseClicked() {
        String name = view.getName();
        client.sendName(name);
    }
}
