package ru.shift.client.presenter;

import ru.shift.client.model.HttpClient;
import ru.shift.client.view.concrete.ConnectView;

public class ConnectPresenter implements Presenter {
    private final ConnectView view;
    private final HttpClient client;

    public ConnectPresenter(ConnectView view, HttpClient client) {
        this.view = view;
        this.client = client;
        view.addActionListener(this);
    }

    @Override
    public void onMouseClicked() {
        String serverAddress = view.getServerAddress();
        client.connect(serverAddress);
    }
}
