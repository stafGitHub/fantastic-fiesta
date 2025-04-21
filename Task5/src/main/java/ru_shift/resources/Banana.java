package ru_shift.resources;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class Banana implements Resource {
    private final int id;
    private static int nextId = 1;

    public Banana() {
        this.id = nextId++;
        log.trace("Created new banana: {}", this);
    }

    @Override
    public int getId() {
        return id;
    }
}