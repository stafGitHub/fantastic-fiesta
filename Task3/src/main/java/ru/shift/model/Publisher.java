package ru.shift.model;


import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;

public interface Publisher {
    void addListener(GameSettingsListener gameSettingsListener);

    void notifyListeners(GameEvent gameEvent);
}
