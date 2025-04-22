package ru.shift.events;


import ru.shift.model.events.GameEvent;

public interface Publisher {
    void addListener(Observer observer);

    void notifyListeners(GameEvent gameEvent);
}
