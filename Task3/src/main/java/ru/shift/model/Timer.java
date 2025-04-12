package ru.shift.model;

import lombok.Getter;
import ru.shift.model.listeners.ModelViewFieldListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Timer {
    @Getter
    private final AtomicInteger secondsPassed = new AtomicInteger(0);
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ModelViewFieldListener listener;
    private volatile boolean running;

    public Timer(ModelViewFieldListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;

        executor.scheduleAtFixedRate(() -> {
            int currentSeconds = secondsPassed.incrementAndGet();
            listener.timeUpdate(currentSeconds);
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        running = false;
        executor.shutdownNow();
    }

    public void reset() {
        stop();
        secondsPassed.set(0);
        executor = Executors.newSingleThreadScheduledExecutor();
    }
}