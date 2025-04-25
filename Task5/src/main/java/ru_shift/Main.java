package ru_shift;

import lombok.extern.slf4j.Slf4j;
import ru_shift.configuration.Config;
import ru_shift.configuration.ErrorConfiguration;
import ru_shift.factory.BananaFactory;

@Slf4j
public class Main {
    private static final int RUNTIME_TIME = 5_000;
    public static void main(String[] args) {
        Config config = null;
        try {
            config = new Config("application.properties");
        }catch (ErrorConfiguration errorConfiguration) {
            log.warn(errorConfiguration.getMessage());
            System.exit(0);
        }

        BananaFactory factory = new BananaFactory(config);
        factory.start();

        try {
            Thread.sleep(RUNTIME_TIME);
            factory.shutdown();
        } catch (InterruptedException e) {
            log.info("Главный поток прерван");
            Thread.currentThread().interrupt();
        }
    }
}