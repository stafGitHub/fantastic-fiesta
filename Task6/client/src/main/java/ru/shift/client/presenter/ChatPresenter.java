package ru.shift.client.presenter;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.model.event.Message;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.network.MessageType;
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
                chatView.addMessage(chatMessage.departureDate().toString() + "-" + chatMessage.sender() + ": " + chatMessage.message());
            }

            if (serverMessage instanceof UsersMessage usersMessage) {
                log.info("Получен список пользователей: {}", usersMessage);
                chatView.addUser(usersMessage.users());
            }

            if (serverMessage instanceof SystemMessage systemMessage) {
                log.info("Получено системное сообщение: {}", systemMessage);
                var systemMessageStatus = systemMessage.systemMessageStatus();
                chatView.addMessage(systemMessage.systemMessageStatus().name() + systemMessage.sender());

                switch (systemMessageStatus) {
                    case LOGIN -> {
                        chatView.addUser(systemMessage.sender());
                        log.info("Добавлен пользователь: {}", systemMessage.sender());
                    }
                    case LOGOUT -> {
                        chatView.removeUser(systemMessage.sender());
                        log.info("Пользователь удалён: {}", systemMessage.sender());
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
