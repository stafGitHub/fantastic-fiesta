package ru.shift.client.presenter;

import ru.shift.client.model.UserConnect;
import ru.shift.client.view.WindowManager;

import java.io.IOException;

public class ConnectPresenter implements Presenter {
    private final UserConnect userConnect = UserConnect.INSTANCE;
    private final WindowManager windowManager = WindowManager.INSTANCE;

    public ConnectPresenter() {
        windowManager.getConnectView().addActionListener(this);
    }

    @Override
    public void onButtonClick() {
        var split = windowManager.getConnectView().getServerAddress().split(":");
        try {
            userConnect.connect(split[0], Integer.parseInt(split[1]));
            windowManager.getConnectView().setVisible(false);
            windowManager.getConnectNameView().setVisible(true);
        } catch (IOException e) {
            windowManager.getConnectView().showError("Сервер не найден");
        }
    }
}
