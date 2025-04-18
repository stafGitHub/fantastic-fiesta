package ru.shift.view.observers.game.status;

import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.game.status.GameOver;
import ru.shift.model.filed.Field;
import ru.shift.view.windows.MainWindow;


public class GameOverObserver extends Observer implements Publisher {
    private final MainWindow mainWindow;
    private final Field fields;

    public GameOverObserver(MainWindow mainWindow , Publisher publisher, Field fields) {
        super(publisher);
        this.mainWindow = mainWindow;
        this.fields = fields;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof GameOver){

        }
    }
}
