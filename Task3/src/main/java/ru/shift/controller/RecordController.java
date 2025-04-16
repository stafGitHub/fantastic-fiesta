package ru.shift.model.records;

import lombok.RequiredArgsConstructor;
import ru.shift.model.events.RecordNameListener;

@RequiredArgsConstructor
public class RecordController implements RecordNameListener {
    private final RecordManager recordManager;
    @Override
    public void onRecordNameEntered(String name) {
        recordManager.setRecordName(name);
    }
}
