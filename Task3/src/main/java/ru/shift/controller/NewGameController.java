package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.model.listners.ControllerModelListener;
import ru.shift.controller.listners.NewGameListener;

@RequiredArgsConstructor
public class NewGameController implements NewGameListener {
    private final ControllerModelListener controllerModelListener;

    @Override
    public void newGame() {
        controllerModelListener.newGame();
    }
}
