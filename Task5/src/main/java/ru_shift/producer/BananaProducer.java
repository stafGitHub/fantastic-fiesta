package ru_shift.producer;

import lombok.extern.slf4j.Slf4j;
import ru_shift.resources.Banana;
import ru_shift.storage.Storage;

@Slf4j
public class BananaProducer implements Producer {
    private final int id;
    private final int productionTime;
    private final Storage storage;
    private static final Object lock = new Object();
    private static int nextId = 1;

    public BananaProducer(int productionTime, Storage storage) {
        synchronized (lock) {
            this.id = nextId++;
            this.productionTime = productionTime;
            this.storage = storage;
            log.info("Создан производитель {}", id);
        }
    }

    @Override
    public void run() {
        log.info("Производитель {} начал работу", id);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(productionTime);
                Banana banana = new Banana();
                storage.addResource(banana);
                log.debug("Произведен банан {}", banana.getId());
            }
        } catch (InterruptedException e) {
            log.warn("Производитель {} прерван", id);
            Thread.currentThread().interrupt();
        } finally {
            log.info("Производитель {} завершил работу", id);
        }
    }


    @Override
    public int getProducerId() {
        return id;
    }
}