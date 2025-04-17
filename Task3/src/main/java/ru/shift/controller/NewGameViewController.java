package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.ControllerModelNewGameListener;
import ru.shift.view.listeners.ViewControllerNewGameListener;

@Slf4j
@RequiredArgsConstructor
public class NewGameViewController implements ViewControllerNewGameListener {
    private final ControllerModelNewGameListener controllerModelListener;

    @Override
    public void newGame() {
        log.info("Обработка нажатия новой игры");
        controllerModelListener.newGame();
    }
}
