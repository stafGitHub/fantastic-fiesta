package ru.shift.view.observers.game.status;

import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.game.result.Lose;
import ru.shift.view.windows.LoseWindow;

public class GameLoseObserver extends Observer {
    private final LoseWindow loseWindow;

    public GameLoseObserver(Publisher publisher, LoseWindow loseWindow) {
        super(publisher);
        this.loseWindow = loseWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof Lose) {
            loseWindow.setVisible(true);
        }
    }
}
