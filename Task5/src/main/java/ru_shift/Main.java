package ru_shift;

import lombok.extern.slf4j.Slf4j;
import ru_shift.configuration.Config;
import ru_shift.configuration.ConfigurationException;
import ru_shift.factory.BananaFactory;
import ru_shift.factory.ConsoleProgramRestart;

@Slf4j
public class Main {
    private static final int RUNTIME_TIME = 10_000;

    public static void main(String[] args) {
        Config config = null;
        try {
            config = new Config("application.properties");
        } catch (ConfigurationException configurationException) {
            log.warn(configurationException.getMessage());
            System.exit(0);
        }

        BananaFactory factory = new BananaFactory(config);

        start(factory);
    }

    private static void start(BananaFactory factory) {
        try {
            while (true) {
                factory.start();
                Thread.sleep(RUNTIME_TIME);
                factory.shutdown();
                log.info("Состояние склада {}", factory.getStorage());
                if (!ConsoleProgramRestart.restart()) {
                    break;
                }
                factory.restart();
            }

        } catch (InterruptedException e) {
            log.info("Главный поток прерван");
            factory.shutdown();
            Thread.currentThread().interrupt();
        }
    }
}