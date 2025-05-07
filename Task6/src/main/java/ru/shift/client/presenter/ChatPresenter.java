package ru.shift.client.presenter;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.model.event.Message;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.common.network.ApplicationProtocol;
import ru.shift.common.network.message.ClientMessage;
import ru.shift.common.network.server.SendMessage;
import ru.shift.common.network.message.ServerMessage;
import ru.shift.common.network.server.SystemMessage;
import ru.shift.common.network.server.UsersMessage;

@Slf4j
public class ChatPresenter extends Observer implements Presenter {
    private final ChatView chatView;

    public ChatPresenter(ChatView chatView) {
        super(userConnect);
        this.chatView = chatView;
        chatView.addActionListener(this);
    }

    @Override
    public void event(Event event) {
        if (event instanceof Message message) {
            ServerMessage serverMessage = message.serverMessage();

            if (serverMessage instanceof SendMessage sendMessage) {
                log.info("Получено сообщение: {}", sendMessage);
                chatView.addMessage(sendMessage.localDate().toString() + "-" + sendMessage.name() + ": " + sendMessage.body());
            }

            if (serverMessage instanceof UsersMessage usersMessage) {
                log.info("Получен список пользователей: {}", usersMessage);
                chatView.addUser(usersMessage.users());
            }

            if (serverMessage instanceof SystemMessage systemMessage) {
                log.info("Получено системное сообщение: {}", systemMessage);
                var systemMessageStatus = systemMessage.systemMessageStatus();
                chatView.addMessage(systemMessage.systemMessageStatus().getMessage() + systemMessage.name());

                switch (systemMessageStatus) {
                    case LOGIN ->{
                        chatView.addUser(systemMessage.name());
                        log.info("Добавлен пользователь: {}", systemMessage.name());
                    }
                    case LOGOUT ->{
                        chatView.removeUser(systemMessage.name());
                        log.info("Пользователь удалён: {}", systemMessage.name());
                    }
                }
            }
        }
    }

    @Override
    public void onButtonClick() {
        var messages = chatView.getMessages();
        log.info("Отправка сообщения: {}", messages);
        userConnect.sendMessage(new ClientMessage(ApplicationProtocol.SEND_MESSAGE, messages));
    }
}
