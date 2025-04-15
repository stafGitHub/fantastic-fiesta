package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.fields.TimeUpdate;
import ru.shift.view.windows.MainWindow;

public class TimeObserver extends GameSettingsListener {
    private final MainWindow mainWindow;
    public TimeObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof TimeUpdate(int time)){
            mainWindow.setTimerValue(time);
        }
    }
}
