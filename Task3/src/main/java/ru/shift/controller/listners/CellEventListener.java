package ru.shift.controller.listners;

import ru.shift.view.ButtonType;

public interface CellEventListener {
    void onMouseClick(int x, int y, ButtonType buttonType);
}
