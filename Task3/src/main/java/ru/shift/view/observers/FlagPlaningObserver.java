package ru.shift.view.observers;

import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.fields.FlagPlaning;
import ru.shift.view.GameImage;
import ru.shift.view.windows.MainWindow;

public class FlagPlaningObserver extends GameSettingsListener {
    private final MainWindow mainWindow;

    public FlagPlaningObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof FlagPlaning(int row, int column, boolean flag)){
            if (flag){
                mainWindow.setCellImage(row, column,GameImage.MARKED);
            }else {
                mainWindow.setCellImage(row, column,GameImage.CLOSED);
            }
        }
    }
}
