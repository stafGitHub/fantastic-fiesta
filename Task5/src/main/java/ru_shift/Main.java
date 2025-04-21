package ru_shift;

import lombok.extern.slf4j.Slf4j;
import ru_shift.factory.BananaFactory;
import ru_shift.factory.FactoryConfig;

@Slf4j
public class Main {
    private static final int RUNTIME_TIME = 15_000;
    public static void main(String[] args) {
        BananaFactory factory = new BananaFactory(new FactoryConfig());
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