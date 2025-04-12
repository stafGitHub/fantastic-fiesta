package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.view.listeners.ViewControllerNewGameListener;
import ru.shift.controller.listeners.ControllerModelNewGameListener;

@RequiredArgsConstructor
public class NewGameViewController implements ViewControllerNewGameListener {
    private final ControllerModelNewGameListener controllerModelListener;

    @Override
    public void newGame() {
        controllerModelListener.newGame();
    }
}
