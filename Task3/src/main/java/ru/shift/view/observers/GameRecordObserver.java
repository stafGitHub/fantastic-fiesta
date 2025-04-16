package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.record.NewRecord;
import ru.shift.view.windows.RecordsWindow;

public class GameRecordObserver extends GameSettingsListener {
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
