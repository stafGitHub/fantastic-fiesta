package ru.shift.client.presenter;

import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.model.UserConnect;
import ru.shift.client.model.event.Message;
import ru.shift.client.view.WindowManager;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.common.protocol.ApplicationProtocol;
import ru.shift.common.protocol.message.ClientMessage;
import ru.shift.common.protocol.message.output.SendMessage;
import ru.shift.common.protocol.message.output.ServerMessage;

import java.util.Calendar;

public class ChatPresenter extends Observer implements Presenter {
    public final UserConnect userConnect = UserConnect.INSTANCE;
    public final ChatView chatView = WindowManager.INSTANCE.getChatView();

    public ChatPresenter() {
        super(UserConnect.INSTANCE);
        chatView.addActionListener(this);
    }

    @Override
    public void event(Event event) {
        if (event instanceof Message message) {
            ServerMessage serverMessage = message.serverMessage();
            if (serverMessage instanceof SendMessage sendMessage){
                chatView.addMessage(sendMessage.body());
            }
        }
    }

    @Override
    public void onButtonClick() {
        var messages = chatView.getMessages();
        userConnect.sendMessage(new ClientMessage(ApplicationProtocol.SEND_MESSAGE, messages, Calendar.getInstance()));
    }
}
