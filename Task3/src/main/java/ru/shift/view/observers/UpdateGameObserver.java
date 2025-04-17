package ru.shift.view.observers;

import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.events.fields.UpdateGame;
import ru.shift.view.windows.MainWindow;

public class UpdateGameObserver extends Observer {
    private final MainWindow mainWindow;

    public UpdateGameObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateGame updateGame) {
            mainWindow.createGameField(updateGame.gameType().rows, updateGame.gameType().cols);
        }
    }
}
