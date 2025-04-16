package ru.shift.events;


public interface Publisher {
    void addListener(Observer observer);

    void notifyListeners(GameEvent gameEvent);
}
