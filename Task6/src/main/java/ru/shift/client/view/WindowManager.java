package ru.shift.client.view;

import lombok.Getter;
import ru.shift.client.model.UserConnect;
import ru.shift.client.view.concrete.ChatView;
import ru.shift.client.view.concrete.ConnectNameView;
import ru.shift.client.view.concrete.ConnectView;
import ru.shift.common.network.ApplicationProtocol;
import ru.shift.common.network.request.ClientMessage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
public enum WindowManager {
    INSTANCE;

    private final ConnectView connectView = new ConnectView();
    private final ConnectNameView connectNameView = new ConnectNameView();
    private final ChatView chatView = new ChatView();

    {
        setupWindowClosing(connectView);
        setupWindowClosing(connectNameView);
        setupWindowClosing(chatView);
    }

    private void setupWindowClosing(JFrame window) {
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserConnect.INSTANCE.sendMessage(new ClientMessage(ApplicationProtocol.LOGOUT, null));

                UserConnect.INSTANCE.shutdown();
                window.dispose();

                System.exit(0);

            }
        });
    }
}
