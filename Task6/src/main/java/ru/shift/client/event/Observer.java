package ru.shift.client.event;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.model.event.Event;

@Slf4j
public abstract class Observer {
    protected Observer(Publisher... publisher) {
        for (Publisher pub : publisher) {
            subscribe(pub);
        }

    }

    public abstract void serverEvent(Event event);

    void subscribe(Publisher publisher) {
        log.debug("{} подписался на {}", this, publisher);
        publisher.addListener(this);
    }
}
