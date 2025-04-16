package ru.shift.view.observers;

import ru.shift.events.Publisher;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.game.result.Won;
import ru.shift.view.windows.WinWindow;

public class GameWonObserver extends Observer {
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
