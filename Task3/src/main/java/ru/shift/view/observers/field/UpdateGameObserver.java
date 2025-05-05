package ru.shift.view.observers.field;

import lombok.extern.slf4j.Slf4j;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.fields.UpdateGame;
import ru.shift.view.windows.MainWindow;

@Slf4j
public class UpdateGameObserver extends Observer {
    private final MainWindow mainWindow;

    public UpdateGameObserver(MainWindow mainWindow, Publisher... publisher) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateGame updateGame) {
            log.info("Создан новый gameField");
            mainWindow.createGameField(updateGame.gameType().rows, updateGame.gameType().cols);
            mainWindow.resetUI(updateGame.gameType());
        }
    }
}
