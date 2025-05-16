package ru.shift.client.view;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.event.Event;
import ru.shift.client.event.Observer;
import ru.shift.client.event.Publisher;
import ru.shift.client.model.UserConnect;
import ru.shift.client.presenter.event.NextWindow;
import ru.shift.network.model.MessageType;
import ru.shift.network.message.ClientMessage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WindowManager extends Observer {
    private final List<JFrame> windows = new ArrayList<>();

    public WindowManager(Publisher... publisher) {
        super(publisher);
    }

    private int currentWindow = 0;

    @Override
    public void event(Event event) {
        if (event instanceof NextWindow) {
            currentWindow++;
            var jFrame = windows.get(currentWindow);
            jFrame.setVisible(true);
            log.info("Переключение окна {} -> {}",
                    windows.get(currentWindow - 1).getClass().getSimpleName(),
                    windows.get(currentWindow).getClass().getSimpleName());
        }
    }

    public void chainWindow(JFrame... window) {
        for (JFrame jFrame : window) {
            setupWindowClosing(jFrame);
            windows.add(jFrame);
        }

    }

    private void setupWindowClosing(JFrame window) {
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                log.info("Закрытие окна: {}", windows.get(currentWindow).getClass().getSimpleName());

                if (UserConnect.INSTANCE.isConnected()) {
                    UserConnect.INSTANCE.sendMessage(new ClientMessage(MessageType.LOGOUT, null));
                }

                UserConnect.INSTANCE.shutdown();
                window.dispose();

                System.exit(0);

            }
        });
    }

    public void run() {
        SwingUtilities.invokeLater(() -> windows.get(currentWindow).setVisible(true));
    }
}
