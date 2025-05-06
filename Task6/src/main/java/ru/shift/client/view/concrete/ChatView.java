package ru.shift.client.view.concrete;

import ru.shift.client.presenter.Presenter;
import ru.shift.client.view.generative.Chat;

import java.util.List;

public class ChatView extends Chat {
    public ChatView() {
        super();
    }

    public void addUser(List<String> users) {
    }

    public void addActionListener(Presenter presenter) {
        PushButton.addActionListener(e -> presenter.onButtonClick());
    }

    public void addMessage(String message) {
        String current = ChatPane.getText();
        ChatPane.setText(current + (current.isEmpty() ? "" : "\n") + message);
    }

    public String getMessages() {
       return messageField.getText();
    }


}