package ru.shift.client.presenter;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.model.event.Message;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.network.model.MessageType;
import ru.shift.network.message.ClientMessage;
import ru.shift.network.message.ServerMessage;
import ru.shift.network.model.ChatMessage;
import ru.shift.network.model.SystemMessage;
import ru.shift.network.model.UsersMessage;

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

            if (serverMessage instanceof ChatMessage chatMessage) {
                log.info("Получено сообщение: {}", chatMessage);
                chatView.addMessage(chatMessage.getDispatchDate().toString() + "-" + chatMessage.getSender() + ": " + chatMessage.getMessage());
            }

            if (serverMessage instanceof UsersMessage usersMessage) {
                log.info("Получен список пользователей: {}", usersMessage);
                chatView.addUser(usersMessage.getUsers());
            }

            if (serverMessage instanceof SystemMessage systemMessage) {
                log.info("Получено системное сообщение: {}", systemMessage);
                var systemMessageStatus = systemMessage.getMessageStatus();

                switch (systemMessageStatus) {
                    case LOGIN -> {
                        chatView.addMessage("Подключился: " + systemMessage.getSender());
                        chatView.addUser(systemMessage.getSender());
                        log.info("Добавлен пользователь: {}", systemMessage.getSender());
                    }
                    case LOGOUT -> {
                        chatView.addMessage("Отключился: " + systemMessage.getSender());
                        chatView.removeUser(systemMessage.getSender());
                        log.info("Пользователь удалён: {}", systemMessage.getSender());
                    }
                }
            }
        }
    }

    @Override
    public void onButtonClick() {
        var messages = chatView.getMessages();
        log.info("Отправка сообщения: {}", messages);
        userConnect.sendMessage(new ClientMessage(MessageType.CHAT_MESSAGE, messages));
    }
}
