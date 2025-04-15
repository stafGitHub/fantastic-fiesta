package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.game.result.Lose;
import ru.shift.view.windows.LoseWindow;

public class GameLoseObserver extends GameSettingsListener {
    private final LoseWindow loseWindow;
    public GameLoseObserver(Publisher publisher, LoseWindow loseWindow) {
        super(publisher);
        this.loseWindow = loseWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof Lose){
            loseWindow.setVisible(true);
        }
    }
}
