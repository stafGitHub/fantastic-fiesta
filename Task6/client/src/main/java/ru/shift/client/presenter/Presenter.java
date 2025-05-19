package ru.shift.client.presenter;

import ru.shift.client.model.UserConnect;

public interface Presenter {
    UserConnect userConnect = UserConnect.INSTANCE;

    void onButtonClick();
}
