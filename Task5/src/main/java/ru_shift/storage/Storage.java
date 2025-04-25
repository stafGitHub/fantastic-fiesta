package ru_shift.storage;

import ru_shift.resources.Resource;

public interface Storage {
    void addResource(Resource resource) throws InterruptedException;
    Resource takeResource() throws InterruptedException;
}