package ru.shift.client.event;

import java.util.ArrayList;
import java.util.List;

public interface Publisher {
    List<Observer> observers = new ArrayList<>();

    default void addListener(Observer observer) {
        observers.add(observer);
    }

    default void notifyListeners(Event event) {
        for (Observer observer : observers) {
            observer.event(event);
        }
    }
}
