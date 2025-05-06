package ru.shift.client;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.presenter.ChatPresenter;
import ru.shift.client.presenter.ConnectNamePresenter;
import ru.shift.client.presenter.ConnectPresenter;
import ru.shift.client.view.WindowManager;

import javax.swing.*;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception ignored) {
        }

        var connectPresenter = new ConnectPresenter();
        var connectNamePresenter = new ConnectNamePresenter();
        var chatPresenter = new ChatPresenter();

        SwingUtilities.invokeLater(() -> WindowManager.INSTANCE.getConnectView().setVisible(true));

    }
}