package ru.shift.timer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.events.fields.TimeUpdate;
import ru.shift.events.game.status.FirstClick;
import ru.shift.events.game.status.GameOver;
import ru.shift.events.game.status.NewGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Timer extends Observer implements Publisher {
    @Getter
    private final AtomicInteger secondsPassed = new AtomicInteger(0);
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private volatile boolean running;
    private final List<Observer> observers = new ArrayList<>();

    public Timer(Publisher publisher) {
        super(publisher);
        log.info("Таймер создан");
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof FirstClick) {
            start();
        } else if (gameEvent instanceof GameOver) {
            stop();
        } else if (gameEvent instanceof NewGame) {
            reset();
        }
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        observers.forEach(observer -> observer.onGameEvent(gameEvent));
    }

    private void start() {
        if (running) {
            return;
        }
        running = true;

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            int currentSeconds = secondsPassed.incrementAndGet();
            notifyListeners(new TimeUpdate(currentSeconds));

        }, 1, 1, TimeUnit.SECONDS);
    }

    private void stop() {
        log.info("Таймер остановлен");
        if (!running) {
            return;
        }
        running = false;
        executor.shutdownNow();

    }

    private void reset() {
        stop();
        secondsPassed.set(0);
        log.info("Таймер сброшен");
    }

}