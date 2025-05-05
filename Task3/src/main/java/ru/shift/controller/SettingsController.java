package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.SettingsListeners;
import ru.shift.model.GameType;
import ru.shift.view.listeners.GameTypeListener;

@Slf4j
@RequiredArgsConstructor
public class SettingsController implements GameTypeListener {
    private final SettingsListeners gameModel;

    @Override
    public void onGameTypeChanged(GameType gameType) {
        log.info("Обработка изменения сложности игры : {}", gameType);
        gameModel.changeDifficulty(gameType);
    }
}
