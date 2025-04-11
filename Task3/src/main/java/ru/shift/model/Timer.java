package ru.shift.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.shift.model.listners.ViewModelListener;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class Timer {
    private Thread timerThread;
    @Getter
    private final AtomicInteger secondsPassed = new AtomicInteger(0);
    private final ViewModelListener listener;
    private volatile boolean running;

    public void start() {
        if (running) {
            return;
        }

        running = true;

        timerThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                    secondsPassed.incrementAndGet();
                    listener.timeUpdate(secondsPassed.intValue());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        timerThread.start();
    }

    public void stop() {
        running = false;
        if (timerThread != null) {
            timerThread.interrupt();
            timerThread = null;
        }
    }

    public void reset() {
        stop();
        secondsPassed.set(0);
    }

}
