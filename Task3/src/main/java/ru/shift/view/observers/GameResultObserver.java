package ru.shift.view.observers;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.shift.controller.NewGameViewController;
import ru.shift.model.listeners.ModelViewGameResultListener;
import ru.shift.view.windows.LoseWindow;
import ru.shift.view.windows.MainWindow;
import ru.shift.view.windows.WinWindow;

@RequiredArgsConstructor
public class GameResultObserver implements ModelViewGameResultListener {
    private final LoseWindow loseWindow;
    private final WinWindow winWindow;

    @Override
    public void loseGame() {
        loseWindow.setVisible(true);

    }

    @Override
    public void winGame() {
        winWindow.setVisible(true);
    }
}
