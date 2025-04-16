package ru.shift.view.observers;

import ru.shift.events.Publisher;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.record.NewRecord;
import ru.shift.view.windows.RecordsWindow;

public class GameRecordObserver extends Observer {
    private final RecordsWindow recordsWindow;
    public GameRecordObserver(Publisher publisher,
                               RecordsWindow recordsWindow) {
        super(publisher);
        this.recordsWindow = recordsWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof NewRecord) {
            recordsWindow.setVisible(true);
        }
    }
}
