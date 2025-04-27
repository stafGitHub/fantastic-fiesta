package ru_shift;

import lombok.extern.slf4j.Slf4j;
import ru_shift.configuration.ConfigurationLoader;
import ru_shift.configuration.ConfigurationProperties;
import ru_shift.exceptions.ConfigurationException;
import ru_shift.factory.BananaFactory;
import ru_shift.util.ConsoleProgramRestart;

@Slf4j
public class Main {
    private static final int RUNTIME_TIME = 10_000;
    private static final String CONFIGURATION_FILE = "application.properties";


    public static void main(String[] args) {
        ConfigurationProperties configurationProperties = null;
        try {
            configurationProperties = ConfigurationLoader.getProperties(CONFIGURATION_FILE);
        } catch (ConfigurationException configurationException) {
            log.error(configurationException.getMessage(), configurationException);
            System.exit(0);
        }

        BananaFactory factory = new BananaFactory(configurationProperties);

        start(factory);
    }

    private static void start(BananaFactory factory) {
        try {
            do {
                factory.start();
                Thread.sleep(RUNTIME_TIME);
                factory.shutdown();
                log.info("Состояние склада {}", factory.getStorage());
            } while (ConsoleProgramRestart.restart());

        } catch (InterruptedException e) {
            log.info("Главный поток прерван");
            factory.shutdown();
            Thread.currentThread().interrupt();
        }
    }
}