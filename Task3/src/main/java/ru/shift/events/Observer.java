package ru.shift.events;

import lombok.extern.slf4j.Slf4j;
import ru.shift.model.events.GameEvent;

@Slf4j
public abstract class Observer {
    protected Observer(Publisher... publisher) {
        for (Publisher pub : publisher) {
            subscribe(pub);
        }

    }

    public abstract void onGameEvent(GameEvent gameEvent);

    void subscribe(Publisher publisher) {
        log.debug("{} подписался на {}", this, publisher);
        publisher.addListener(this);
    }

}
