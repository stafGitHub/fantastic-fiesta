package ru.shift.view.observers;

import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.events.fields.UpdateBombCount;
import ru.shift.view.windows.MainWindow;

public class BombCountObserver extends Observer {
    private final MainWindow mainWindow;

    public BombCountObserver(Publisher publisher, MainWindow mainWindow) {
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
