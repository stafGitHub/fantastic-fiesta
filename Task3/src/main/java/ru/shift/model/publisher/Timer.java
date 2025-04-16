package ru.shift.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.fields.TimeUpdate;
import ru.shift.model.events.game.status.FirstClick;
import ru.shift.model.events.game.status.GameOver;
import ru.shift.model.events.game.status.NewGame;
import ru.shift.view.observers.TimeObserver;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Timer extends GameSettingsListener {
    @Getter
    private final AtomicInteger secondsPassed = new AtomicInteger(0);
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final TimeObserver timeObserver;
    private volatile boolean running;

    public Timer(TimeObserver timeObserver, Publisher publisher) {
        super(publisher);
        this.timeObserver = timeObserver;
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

    private void start() {
        if (running) {
            return;
        }
        running = true;

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            int currentSeconds = secondsPassed.incrementAndGet();
            timeObserver.onGameEvent(new TimeUpdate(currentSeconds));

        }, 1, 1, TimeUnit.SECONDS);
    }

    private void stop() {
        if (!running) {
            return;
        }
        running = false;
        executor.shutdownNow();

    }

    private void reset() {
        stop();
        secondsPassed.set(0);
    }

}