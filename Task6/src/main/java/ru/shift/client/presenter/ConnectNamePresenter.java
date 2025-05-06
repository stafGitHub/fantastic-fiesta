package ru.shift.client.presenter;

import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.model.UserConnect;
import ru.shift.client.model.event.Message;
import ru.shift.client.view.WindowManager;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.LoginMessageError;
import ru.shift.common.protocol.message.output.LoginMessageSuccess;

import java.util.Calendar;

public class ConnectNamePresenter extends Observer implements Presenter {
    private final WindowManager windowManager = WindowManager.INSTANCE;

    public ConnectNamePresenter() {
        super(UserConnect.INSTANCE);
        windowManager.getConnectNameView().addActionListener(this);
    }

    @Override
    public void onButtonClick() {
        var userName = windowManager.getConnectNameView().getUserName();
        userConnect.sendMessage(new ClientMessage(ApplicationProtocol.LOGIN,userName, Calendar.getInstance()));

    }

    @Override
    public void event(Event event) {
        if (event instanceof Message serverMessage){
            var message = serverMessage.serverMessage();
            if (message instanceof LoginMessageSuccess){
                windowManager.getConnectNameView().setVisible(false);
                windowManager.getChatView().setVisible(true);
                userConnect.sendMessage(new ClientMessage(ApplicationProtocol.GET_USERS,null,Calendar.getInstance()));
            }else if (message instanceof LoginMessageError loginMessageError){
                windowManager.getConnectNameView().showError(loginMessageError.toString());
            }
        }
    }
}
