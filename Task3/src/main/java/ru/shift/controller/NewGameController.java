package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.view.listeners.NewGameListener;

@Slf4j
@RequiredArgsConstructor
public class NewGameController implements NewGameListener {
    private final ru.shift.controller.listeners.NewGameListener controllerModelListener;

    @Override
    public void newGame() {
        log.info("Обработка нажатия новой игры");
        controllerModelListener.newGame();
    }
}
