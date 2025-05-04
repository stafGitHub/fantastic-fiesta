package ru.shift.server;

import lombok.extern.slf4j.Slf4j;
import ru.shift.server.chat.ServerChat;
import ru.shift.server.configuration.Configuration;
import ru.shift.server.configuration.ConfigurationLoader;
import ru.shift.server.expections.ConfigurationException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Configuration configuration = null;

        try {
            configuration = ConfigurationLoader.getConfiguration("application.properties");
        } catch (ConfigurationException e) {
            log.error(e.getMessage());
            System.exit(0);
        }

        var serverChat = new ServerChat(configuration.port());
        serverChat.start();

    }
}
