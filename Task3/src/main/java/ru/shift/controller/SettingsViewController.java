package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.view.listeners.ViewControllerGameTypeListener;
import ru.shift.model.GameType;
import ru.shift.controller.listeners.ControllerModelSettingsListeners;

@RequiredArgsConstructor
public class SettingsViewController implements ViewControllerGameTypeListener {
    private final ControllerModelSettingsListeners gameModel;

    @Override
    public void onGameTypeChanged(GameType gameType) {
        gameModel.changeDifficulty(gameType);
    }
}
