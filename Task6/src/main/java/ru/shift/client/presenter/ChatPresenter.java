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
import ru.shift.common.protocol.message.output.SystemMessage;
import ru.shift.common.protocol.message.output.UsersMessage;

import java.util.Calendar;

public class ChatPresenter extends Observer implements Presenter {
    public final ChatView chatView = WindowManager.INSTANCE.getChatView();

    public ChatPresenter() {
        super(UserConnect.INSTANCE);
        chatView.addActionListener(this);
    }

    @Override
    public void event(Event event) {
        if (event instanceof Message message) {
            ServerMessage serverMessage = message.serverMessage();

            if (serverMessage instanceof SendMessage sendMessage) {
                chatView.addMessage(sendMessage.body());
            }

            if (serverMessage instanceof UsersMessage usersMessage) {
                chatView.addUser(usersMessage.users());
            }

            if (serverMessage instanceof SystemMessage systemMessage) {
                var systemMessageStatus = systemMessage.systemMessageStatus();

                chatView.addMessage(systemMessage.systemMessageStatus().getMessage() + systemMessage.name());

                switch (systemMessageStatus) {
                    case LOGIN -> chatView.addUser(systemMessage.name());
                    case LOGOUT -> chatView.removeUser(systemMessage.name());
                }
            }
        }
    }

    @Override
    public void onButtonClick() {
        var messages = chatView.getMessages();
        userConnect.sendMessage(new ClientMessage(ApplicationProtocol.SEND_MESSAGE, messages, Calendar.getInstance()));
    }
}
