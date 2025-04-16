package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.game.result.Won;
import ru.shift.view.windows.WinWindow;

public class GameWonObserver extends GameSettingsListener {
    private final WinWindow winWindow;
    public GameWonObserver(Publisher publisher, WinWindow winWindow) {
        super(publisher);
        this.winWindow = winWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof Won){
            winWindow.setVisible(true);
        }
    }
}
