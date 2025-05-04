package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.FieldListener;
import ru.shift.view.ButtonType;
import ru.shift.view.listeners.CellEventListener;

@Slf4j
@RequiredArgsConstructor
public class MouseController implements CellEventListener {
    private final FieldListener gameModel;

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        log.info("Обработка нажатия: {}", buttonType);

        switch (buttonType) {
            case LEFT_BUTTON -> gameModel.openCell(y, x);
            case MIDDLE_BUTTON -> gameModel.flagPlaning(y, x);
            case RIGHT_BUTTON -> gameModel.openAround(y, x);
        }

        log.info("Обработка завершена");
    }

}