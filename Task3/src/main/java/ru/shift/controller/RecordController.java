package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.controller.listeners.RecordNameListener;
import ru.shift.record.RecordManager;

@RequiredArgsConstructor
public class RecordController implements RecordNameListener {
    private final RecordManager recordManager;
    @Override
    public void onRecordNameEntered(String name) {
        recordManager.setRecordName(name);
    }
}
