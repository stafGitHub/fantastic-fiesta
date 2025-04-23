package ru.shift.view.observers.field;

import lombok.extern.slf4j.Slf4j;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.fields.UpdateTheCell;
import ru.shift.view.GameImage;
import ru.shift.view.factory.ImageFactory;
import ru.shift.view.windows.MainWindow;

@Slf4j
public class UpdateTheCellObserver extends Observer {
    private final MainWindow mainWindow;


    public UpdateTheCellObserver(MainWindow mainWindow, Publisher... publisher) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateTheCell updateTheCell) {
            updateTheCell.cellOutput()
                    .forEach(cellOutput -> {
                        GameImage image = ImageFactory.getImageForCell(cellOutput);
                        if (image != null) {
                            mainWindow.setCellImage(
                                    cellOutput.y(),
                                    cellOutput.x(),
                                    image
                            );
                            log.debug("Установка результата во вью : y - {} , x - {} , img - {}",
                                    cellOutput.y(),
                                    cellOutput.x(),
                                    image);
                        }
                    });
        }
    }


}
