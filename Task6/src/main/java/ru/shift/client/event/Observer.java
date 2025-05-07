package ru.shift.client.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Observer {
    protected Observer(Publisher... publisher) {
        for (Publisher pub : publisher) {
            subscribe(pub);
        }

    }

    public abstract void event(Event event);

    void subscribe(Publisher publisher) {
        log.info("{} подписался на {}", this, publisher);
        publisher.addListener(this);
    }

}
