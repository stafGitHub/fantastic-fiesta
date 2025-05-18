package ru.shift.client.view.concrete;

import ru.shift.client.presenter.Presenter;
import ru.shift.client.view.generative.Connect;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.regex.Pattern;

public class ConnectView extends Connect {
    private static final Pattern ADDRESS_PATTERN =
            Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):\\d{1,5}$");

    public ConnectView() {
        super();
        initValidation();
    }

    private void initValidation() {
        button1.setEnabled(false);

        formattedTextField1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validate();
            }

            public void insertUpdate(DocumentEvent e) {
                validate();
            }

            public void removeUpdate(DocumentEvent e) {
                validate();
            }

            private void validate() {
                String text = formattedTextField1.getText();
                button1.setEnabled(isValidAddress(text));
            }
        });
    }

    private boolean isValidAddress(String address) {
        if (!ADDRESS_PATTERN.matcher(address).matches()) {
            return false;
        }

        String portStr = address.split(":")[1];
        try {
            int port = Integer.parseInt(portStr);
            return port >= 0 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
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