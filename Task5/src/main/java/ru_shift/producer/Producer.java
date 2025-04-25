package ru_shift.producer;

public interface Producer extends Runnable {
    void shutdown();
    void restart();

    int getProducerId();
}