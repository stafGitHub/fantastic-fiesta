package ru.shift.client.view.concrete;

import ru.shift.client.presenter.Presenter;
import ru.shift.client.view.generative.ConnectName;

import javax.swing.*;

public class ConnectNameView extends ConnectName {
    public ConnectNameView() {
        super();
    }

    public String getUserName() {
        return textField1.getText();
    }

    public void setUserName(String name) {
        textField1.setText(name);
    }

    public void addActionListener(Presenter presenter) {
        okButton.addActionListener(e -> presenter.onMouseClicked());
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}