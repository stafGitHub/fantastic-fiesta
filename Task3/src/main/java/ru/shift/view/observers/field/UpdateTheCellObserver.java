package ru.shift.view.observers.field;

import lombok.extern.slf4j.Slf4j;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.UpdateTheCell;
import ru.shift.view.GameImage;
import ru.shift.view.windows.MainWindow;

import java.util.Map;

import static ru.shift.model.dto.Cell.EMPTY_COLUMN;
import static ru.shift.model.dto.Cell.MINE;

@Slf4j
public class UpdateTheCellObserver extends Observer {
    private final MainWindow mainWindow;

    private static final Map<Integer, GameImage> CELL_IMAGES = Map.of(
            MINE, GameImage.BOMB,
            EMPTY_COLUMN, GameImage.EMPTY,
            1, GameImage.NUM_1,
            2, GameImage.NUM_2,
            3, GameImage.NUM_3,
            4, GameImage.NUM_4,
            5, GameImage.NUM_5,
            6, GameImage.NUM_6,
            7, GameImage.NUM_7,
            8, GameImage.NUM_8
    );

    public UpdateTheCellObserver(MainWindow mainWindow, Publisher... publisher) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateTheCell updateTheCell) {
            updateTheCell.cellOutput()
                    .forEach(cellOutput -> {
                        GameImage image = CELL_IMAGES.get(cellOutput.number());
                        if (image != null) {
                            mainWindow.setCellImage(
                                    cellOutput.x(),
                                    cellOutput.y(),
                                    image
                            );

                            log.debug("Установка результата во вью : x - {} , y - {} , img - {}",
                                    cellOutput.x(),
                                    cellOutput.y(),
                                    image);
                        }
                    });
        }
    }


}
