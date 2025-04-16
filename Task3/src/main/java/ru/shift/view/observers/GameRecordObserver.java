package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.records.RecordController;
import ru.shift.view.windows.MainWindow;
import ru.shift.view.windows.RecordsWindow;

public class GameRecordObserver1 extends GameSettingsListener {
    private final MainWindow mainWindow;
    private final RecordController recordController;

    public GameRecordObserver1(Publisher publisher, MainWindow mainWindow, RecordController recordController) {
        super(publisher);
        this.mainWindow = mainWindow;
        this.recordController = recordController;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        var recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordController);
        recordsWindow.setVisible(true);
    }
}
