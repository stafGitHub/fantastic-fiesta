package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.view.listeners.ViewControllerGameTypeListener;
import ru.shift.model.GameType;
import ru.shift.controller.listeners.ControllerModelSettingsListeners;

@Slf4j
@RequiredArgsConstructor
public class SettingsViewController implements ViewControllerGameTypeListener {
    private final ControllerModelSettingsListeners gameModel;

    @Override
    public void onGameTypeChanged(GameType gameType) {
        log.info("Обработка изменения сложности игры : {}", gameType);
        gameModel.changeDifficulty(gameType);
    }
}
