package ru_shift.factory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru_shift.configuration.ConfigurationProperties;
import ru_shift.consumer.BananaConsumer;
import ru_shift.consumer.Consumer;
import ru_shift.producer.BananaProducer;
import ru_shift.producer.Producer;
import ru_shift.storage.BananaStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BananaFactory {
    @Getter
    private final BananaStorage storage;
    private final List<Producer> producers;
    private final List<Consumer> consumers;
    private final List<Thread> threads;

    public BananaFactory(ConfigurationProperties configurationProperties) {
        this.storage = new BananaStorage(configurationProperties.storageSize());
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.threads = new ArrayList<>();

        for (int i = 0; i < configurationProperties.producerCount(); i++) {
            producers.add(new BananaProducer(configurationProperties.producerTime(), storage));
        }
        for (int i = 0; i < configurationProperties.consumerCount(); i++) {
            consumers.add(new BananaConsumer(configurationProperties.consumerTime(), storage));
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

    public void shutdown() {
        log.info("Начало остановки производства");
        threads.forEach(Thread::interrupt);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                log.error("Прерывание при ожидании завершения потока {}", t.getName(), e);
                Thread.currentThread().interrupt();
            }
        });

        threads.clear();

        log.info("Производство остановлено");
    }
}