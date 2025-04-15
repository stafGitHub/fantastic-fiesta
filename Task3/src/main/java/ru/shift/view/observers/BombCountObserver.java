package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.fields.UpdateBombCount;
import ru.shift.view.windows.MainWindow;

public class BombCountObserver extends GameSettingsListener {
    private final MainWindow mainWindow;
    public BombCountObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateBombCount(int count)){
            mainWindow.setBombsCount(count);
        }
    }
}
