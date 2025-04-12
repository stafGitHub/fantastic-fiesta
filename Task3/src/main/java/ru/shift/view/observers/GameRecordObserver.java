package ru.shift.view.observers;

import lombok.RequiredArgsConstructor;
import ru.shift.model.listeners.ModelViewRecordListener;
import ru.shift.model.records.RecordController;
import ru.shift.view.windows.MainWindow;
import ru.shift.view.windows.RecordsWindow;

@RequiredArgsConstructor
public class GameRecordObserver implements ModelViewRecordListener {
    private final MainWindow mainWindow;
    private final RecordController recordController;

    @Override
    public void updateRecord(int time) {
        var recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordController);
        recordsWindow.setVisible(true);
    }
}
