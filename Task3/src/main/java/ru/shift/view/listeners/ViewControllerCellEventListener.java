package ru.shift.view.listeners;

import ru.shift.view.ButtonType;

public interface ViewControllerCellEventListener {
    void onMouseClick(int x, int y, ButtonType buttonType);
}
