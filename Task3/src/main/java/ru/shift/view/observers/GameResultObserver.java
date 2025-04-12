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
    private final MainWindow mainWindow;
    @Setter
    private NewGameViewController newGameController;

    @Override
    public void loseGame() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(e -> newGameController.newGame());
        loseWindow.setExitListener(e -> mainWindow.dispose());
        loseWindow.setVisible(true);

    }

    @Override
    public void winGame() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setNewGameListener(e -> newGameController.newGame());
        winWindow.setExitListener(e -> mainWindow.dispose());
        winWindow.setVisible(true);
    }
}
