package ru.shift.client.view.concrete;

import ru.shift.client.presenter.Presenter;
import ru.shift.client.view.generative.Connect;

import javax.swing.*;

public class ConnectView extends Connect {
    public ConnectView() {
        super();
    }

    public String getServerAddress() {
        return formattedTextField1.getText();
    }

    public void addActionListener(Presenter presenter) {
        button1.addActionListener(e -> presenter.onButtonClick());
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}