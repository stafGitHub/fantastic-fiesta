package ru.shift.client.view.concrete;

import ru.shift.client.presenter.Presenter;
import ru.shift.client.view.generative.Chat;

import javax.swing.*;
import java.util.List;

public class ChatView extends Chat {
    public ChatView() {
        super();
    }

    public void addUser(List<String> users) {
        DefaultListModel<String> model = (listUsers.getModel() instanceof DefaultListModel)
                ? (DefaultListModel<String>) listUsers.getModel()
                : new DefaultListModel<>();

        for (String user : users) {
            if (!model.contains(user)) {
                model.addElement(user);
            }
        }

        listUsers.setModel(model);
    }

    public void addUser(String user) {
        DefaultListModel<String> model = (listUsers.getModel() instanceof DefaultListModel)
                ? (DefaultListModel<String>) listUsers.getModel()
                : new DefaultListModel<>();

        model.addElement(user);

        listUsers.setModel(model);
    }

    public void removeUser(String user) {
        DefaultListModel<String> model = (listUsers.getModel() instanceof DefaultListModel)
                ? (DefaultListModel<String>) listUsers.getModel()
                : new DefaultListModel<>();

        if (model.contains(user)) {
            model.removeElement(user);
        }

        listUsers.setModel(model);
    }


    public void addActionListener(Presenter presenter) {
        pushButton.addActionListener(e -> {
            presenter.onButtonClick();
            messageField.setText("");
        });
    }

    public void addMessage(String message) {
        String current = chatPane.getText();
        chatPane.setText(current + (current.isEmpty() ? "" : "\n") + message);
    }

    public String getMessages() {
        return messageField.getText();
    }


}