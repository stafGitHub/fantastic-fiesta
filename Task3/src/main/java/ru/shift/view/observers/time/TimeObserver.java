package ru.shift.view.observers.time;

import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.timer.events.TimeUpdate;
import ru.shift.view.windows.MainWindow;

public class TimeObserver extends Observer {
    private final MainWindow mainWindow;

    public TimeObserver(MainWindow mainWindow, Publisher... publisher) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof TimeUpdate timeUpdate) {
            mainWindow.setTimerValue(timeUpdate.time());
        }
    }
}
