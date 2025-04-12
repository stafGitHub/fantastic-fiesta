package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.view.listeners.ViewControllerCellEventListener;
import ru.shift.controller.listeners.ControllerModelFieldListener;
import ru.shift.view.ButtonType;

@RequiredArgsConstructor
public class MouseViewController implements ViewControllerCellEventListener {
    private final ControllerModelFieldListener gameModel;

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> gameModel.openCellLeftButton(y, x);
            case MIDDLE_BUTTON -> gameModel.flagPlaning(y, x);
            case RIGHT_BUTTON -> gameModel.openCellWithMouseCell(y, x);
        }
    }

}