package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.view.listeners.ViewControllerCellEventListener;
import ru.shift.controller.listeners.ControllerModelFieldListener;
import ru.shift.view.ButtonType;

@Slf4j
@RequiredArgsConstructor
public class MouseViewController implements ViewControllerCellEventListener {
    private final ControllerModelFieldListener gameModel;

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        log.info("Обработка нажатия: {}", buttonType);
        long startTime = System.nanoTime();

        switch (buttonType) {
            case LEFT_BUTTON -> gameModel.openCellLeftButton(y, x);
            case MIDDLE_BUTTON -> gameModel.flagPlaning(y, x);
            case RIGHT_BUTTON -> gameModel.openCellWithMouseCell(y, x);
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        log.info("Обработка завершена : {}", elapsedTime);
    }

}