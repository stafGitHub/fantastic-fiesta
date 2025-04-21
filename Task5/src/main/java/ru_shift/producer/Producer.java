package ru_shift.producer;

public interface Producer extends Runnable {
    void shutdown();
    int getProducerId();
}