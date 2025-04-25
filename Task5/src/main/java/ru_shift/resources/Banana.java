package ru_shift.resources;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class Banana implements Resource {
    private final int id;
    private static int nextId = 1;
    private static final Object lock = new Object();

    public Banana() {
        synchronized (lock) {
            this.id = nextId++;
        }
        log.trace("Создан новый банан: {}", this);
    }

    @Override
    public int getId() {
        return id;
    }
}