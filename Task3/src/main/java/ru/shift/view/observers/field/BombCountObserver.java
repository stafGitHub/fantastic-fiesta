package ru.shift.view.observers.field;

import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.fields.UpdateBombCount;
import ru.shift.view.windows.MainWindow;

public class BombCountObserver extends Observer {
    private final MainWindow mainWindow;

    public BombCountObserver(MainWindow mainWindow, Publisher... publisher) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateBombCount updateBombCount) {
            mainWindow.setBombsCount(updateBombCount.count());
        }
    }
}
