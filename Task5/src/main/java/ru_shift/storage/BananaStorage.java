package ru_shift.storage;

import lombok.extern.slf4j.Slf4j;
import ru_shift.resources.Resource;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class BananaStorage implements Storage {
    private final int capacity;
    private final Queue<Resource> storage = new LinkedList<>();
    private final Object lock = new Object();

    public BananaStorage(int capacity) {
        this.capacity = capacity;
        log.info("Склад создан, вместимость: {}", capacity);
    }

    @Override
    public void addResource(Resource resource) throws InterruptedException {
        synchronized (lock) {
            while (storage.size() >= capacity) {
                log.warn("Склад полон ({}), ожидание...", storage.size());
                lock.wait();
            }
            storage.add(resource);
            log.info("Добавлен ресурс {}. На складе: {}/{}", resource, storage.size(), capacity);
            lock.notifyAll();
        }
    }

    @Override
    public Resource takeResource() throws InterruptedException {
        synchronized (lock) {
            while (storage.isEmpty()) {
                log.warn("Склад пуст, ожидание...");
                lock.wait();
            }

            Resource resource = storage.poll();
            log.info("!YELLOW!Забран ресурс {}. На складе: {}/{}", resource, storage.size(), capacity);
            lock.notifyAll();
            return resource;
        }
    }

    @Override
    public String toString() {
        return "BananaStorage{" +
                "capacity=" + capacity +
                ", storage=" + storage.size() +
                '}';
    }
}