package ru_shift.consumer;

import lombok.extern.slf4j.Slf4j;
import ru_shift.resources.Resource;
import ru_shift.storage.Storage;

@Slf4j
public class BananaConsumer implements Consumer {
    private final int id;
    private final int consumptionTime;
    private final Storage storage;
    private volatile boolean isRunning = true;
    private static final Object lock = new Object();
    private static int nextId = 1;

    public BananaConsumer(int consumptionTime, Storage storage) {
        synchronized (lock) {
            this.id = nextId++;
            this.consumptionTime = consumptionTime;
            this.storage = storage;
            log.info("Создан потребитель {}", id);
        }
    }

    @Override
    public void run() {
        log.info("Потребитель {} начал работу", id);
        try {
            while (isRunning && !Thread.currentThread().isInterrupted()) {
                Resource resource = storage.takeResource();
                Thread.sleep(consumptionTime);
                log.debug("Обработан ресурс {}", resource.getId());
            }
        } catch (InterruptedException e) {
            log.warn("Потребитель {} прерван", id);
            Thread.currentThread().interrupt();
        } finally {
            log.info("Потребитель {} завершил работу", id);
        }
    }

    @Override
    public void shutdown() {
        log.debug("Остановка потребителя {}", id);
        isRunning = false;
    }

    @Override
    public int getConsumerId() {
        return id;
    }

    @Override
    public void restart() {
        isRunning = true;
    }
}