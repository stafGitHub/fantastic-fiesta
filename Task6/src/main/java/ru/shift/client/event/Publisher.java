package ru.shift.client.event;

public interface Publisher {
    void addListener(Observer observer);

    void notifyListeners(Event event);
}
