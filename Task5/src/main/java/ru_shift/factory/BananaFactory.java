package ru_shift.factory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru_shift.configuration.Config;
import ru_shift.consumer.BananaConsumer;
import ru_shift.producer.BananaProducer;
import ru_shift.storage.BananaStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BananaFactory {
    @Getter
    private final BananaStorage storage;
    private final List<BananaProducer> producers;
    private final List<BananaConsumer> consumers;
    private final List<Thread> threads;

    public BananaFactory(Config config) {
        this.storage = new BananaStorage(config.getStorageSize());
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.threads = new ArrayList<>();

        for (int i = 0; i < config.getProducerCount(); i++) {
            producers.add(new BananaProducer(config.getProducerTime(), storage));
        }
        for (int i = 0; i < config.getConsumerCount(); i++) {
            consumers.add(new BananaConsumer(config.getConsumerTime(), storage));
        }
    }

    public void start() {
        log.info("Запуск фабрики...");
        producers.forEach(p -> {
            Thread thread = new Thread(p, "Producer-" + p.getProducerId());
            threads.add(thread);
            thread.start();
        });
        consumers.forEach(c -> {
            Thread thread = new Thread(c, "Consumer-" + c.getConsumerId());
            threads.add(thread);
            thread.start();
        });
    }

    public void restart() {
        log.info("Перезапуск производства");
        producers.forEach(BananaProducer::restart);
        consumers.forEach(BananaConsumer::restart);
    }

    public void shutdown() {
        log.info("Начало остановки производства");

        producers.forEach(BananaProducer::shutdown);
        consumers.forEach(BananaConsumer::shutdown);

        threads.forEach(Thread::interrupt);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                log.warn("Прерывание при ожидании завершения потока");
                Thread.currentThread().interrupt();
            }
        });

        threads.clear();

        log.info("Производство остановлено");
    }
}