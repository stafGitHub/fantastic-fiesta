package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.model.listners.ControllerModelListener;
import ru.shift.controller.listners.GameTypeListener;
import ru.shift.model.GameType;

@RequiredArgsConstructor
public class SettingsController implements GameTypeListener {
    private final ControllerModelListener gameModel;

    @Override
    public void onGameTypeChanged(GameType gameType) {
        gameModel.changeDifficulty(gameType);
    }
}
