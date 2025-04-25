package ru_shift.consumer;

public interface Consumer extends Runnable {
    void shutdown();

    void restart();

    int getConsumerId();
}