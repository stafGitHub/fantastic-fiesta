package ru.shift.events;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Observer {
    public Observer(Publisher publisher) {
        subscribe(publisher);
    }

    public abstract void onGameEvent(GameEvent gameEvent);

    void subscribe(Publisher publisher) {
        log.debug("{} подписался на {}", this, publisher);
        publisher.addListener(this);
    }

}
