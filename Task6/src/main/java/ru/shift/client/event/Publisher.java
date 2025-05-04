package ru.shift.client.event;

import ru.shift.client.model.event.Event;

public interface Publisher {

    void addListener(Observer... observer);


    void notifyListeners(Event event);


}
