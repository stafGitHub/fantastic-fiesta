package ru.shift.model.events;

import lombok.extern.slf4j.Slf4j;
import ru.shift.model.Publisher;

@Slf4j
public abstract class GameSettingsListener {
    public GameSettingsListener(Publisher publisher) {
        subscribe(publisher);
    }

    public abstract void onGameEvent(GameEvent gameEvent);

    void subscribe(Publisher publisher){
        log.info("{} подписался на {}",this, publisher);
        publisher.addListener(this);
    };
}
