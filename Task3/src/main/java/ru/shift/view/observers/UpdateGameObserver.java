package ru.shift.view.observers;

import ru.shift.model.GameType;
import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.fields.UpdateGame;
import ru.shift.view.windows.MainWindow;

public class UpdateGameObserver extends GameSettingsListener {
    private final MainWindow mainWindow;
    public UpdateGameObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateGame(GameType gameType)){
            mainWindow.createGameField(gameType.rows,gameType.cols);
        }
    }
}
