package ru.shift.view.observers.field;

import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.fields.FlagPlaning;
import ru.shift.view.GameImage;
import ru.shift.view.windows.MainWindow;

public class FlagPlaningObserver extends Observer {
    private final MainWindow mainWindow;

    public FlagPlaningObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof FlagPlaning flagPlaning) {
            if (flagPlaning.flag()) {
                mainWindow.setCellImage(flagPlaning.column(), flagPlaning.row(), GameImage.MARKED);
            } else {
                mainWindow.setCellImage(flagPlaning.column(), flagPlaning.row(), GameImage.CLOSED);
            }
        }
    }
}
