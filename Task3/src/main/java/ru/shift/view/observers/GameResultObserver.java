package ru.shift.view.observers;

import lombok.RequiredArgsConstructor;
import ru.shift.model.events.ModelViewGameResultListener;
import ru.shift.view.windows.LoseWindow;
import ru.shift.view.windows.WinWindow;

@RequiredArgsConstructor
public class GameResultObserver implements ModelViewGameResultListener  {
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
