package ru.shift.view.observers;

import lombok.extern.slf4j.Slf4j;
import ru.shift.model.publisher.GameModel;
import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.fields.UpdateTheCell;
import ru.shift.view.GameImage;
import ru.shift.view.windows.MainWindow;

import java.util.Map;

@Slf4j
public class UpdateTheCellObserver extends GameSettingsListener {
    private final MainWindow mainWindow;

    private static final Map<Integer, GameImage> CELL_IMAGES = Map.of(
            GameModel.MINE, GameImage.BOMB,
            GameModel.EMPTY_COLUMN, GameImage.EMPTY,
            1, GameImage.NUM_1,
            2, GameImage.NUM_2,
            3, GameImage.NUM_3,
            4, GameImage.NUM_4,
            5, GameImage.NUM_5,
            6, GameImage.NUM_6,
            7, GameImage.NUM_7,
            8, GameImage.NUM_8
    );

    public UpdateTheCellObserver(Publisher publisher, MainWindow mainWindow) {
        super(publisher);
        this.mainWindow = mainWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof UpdateTheCell updateTheCell){
            for (int i = 0; i < updateTheCell.playingFieldCells().getColumnRes().size(); i++) {
                int value = updateTheCell.playingFieldCells().getColumnRes().get(i);
                GameImage image = CELL_IMAGES.get(value);
                if (image != null) {
                    mainWindow.setCellImage(
                            updateTheCell.playingFieldCells().getX().get(i),
                            updateTheCell.playingFieldCells().getY().get(i),
                            image
                    );

                    log.debug("Установка результата во вью : x - {} , y - {} , img - {}",
                            updateTheCell.playingFieldCells().getX().get(i),
                            updateTheCell.playingFieldCells().getY().get(i),
                            image);
                }
            }
        }
    }


}
