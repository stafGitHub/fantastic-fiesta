package ru.shift.client;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.presenter.ChatPresenter;
import ru.shift.client.presenter.ConnectNamePresenter;
import ru.shift.client.presenter.ConnectPresenter;
import ru.shift.client.view.WindowManager;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.client.view.concrete.ConnectNameView;
import ru.shift.client.view.concrete.ConnectView;

import javax.swing.*;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Запуск клиента...");
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception ignored) {
            log.warn("Не удалось установить тему FlatDarculaLaf");
        }
        var connectView = new ConnectView();
        var connectNameView = new ConnectNameView();
        var chatView = new ChatView();

        var connectPresenter = new ConnectPresenter(connectView);
        var connectNamePresenter = new ConnectNamePresenter(connectNameView);
        var chatPresenter = new ChatPresenter(chatView);

        var windowManager = new WindowManager(connectPresenter,connectNamePresenter);

        windowManager.chainWindow(connectView, connectNameView, chatView);

        windowManager.run();
    }
}
