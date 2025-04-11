package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.controller.listners.CellEventListener;
import ru.shift.model.listners.ControllerModelListener;
import ru.shift.view.ButtonType;

@RequiredArgsConstructor
public class MouseController implements CellEventListener {
    private final ControllerModelListener gameModel;

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> gameModel.openCellLeftButton(y, x);
            case MIDDLE_BUTTON -> gameModel.flagPlaning(y, x);
//            case RIGHT_BUTTON ->
        }
    }

}