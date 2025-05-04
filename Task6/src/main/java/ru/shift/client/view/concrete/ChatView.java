package ru.shift.client.view.concrete;

import ru.shift.client.presenter.Presenter;
import ru.shift.client.view.generative.Chat;
import javax.swing.*;

public class ChatView extends Chat {
    public ChatView() {
        super();
    }

    public String getMessage() {
        return messageField.getText();
    }

    public void setMessage(String message) {
        messageField.setText(message);
    }

    public JButton getSendButton() {
        return PushButton;
    }

    public JList getUsersList() {
        return ListUsers;
    }

    public JTextPane getChatPane() {
        return ChatPane;
    }

    public void addActionListener(Presenter presenter) {
        PushButton.addActionListener(e -> presenter.onMouseClicked());
    }

    public void addMessage(String message) {
        String current = ChatPane.getText();
        ChatPane.setText(current + (current.isEmpty() ? "" : "\n") + message);
    }

    public void clearMessages() {
        ChatPane.setText("");
    }
}