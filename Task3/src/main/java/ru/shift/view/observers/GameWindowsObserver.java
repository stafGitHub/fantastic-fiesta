package ru.shift.view.observers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.model.GameModel;
import ru.shift.model.GameType;
import ru.shift.model.dto.PlayingFieldCells;
import ru.shift.model.events.ModelViewFieldListener;
import ru.shift.view.GameImage;
import ru.shift.view.windows.MainWindow;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class GameWindowsObserver implements ModelViewFieldListener {
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

    @Override
    public void updateTheCellView(PlayingFieldCells playingFieldCells) {
        for (int i = 0; i < playingFieldCells.getColumnRes().size(); i++) {
            int value = playingFieldCells.getColumnRes().get(i);
            GameImage image = CELL_IMAGES.get(value);
            if (image != null) {
                mainWindow.setCellImage(
                        playingFieldCells.getX().get(i),
                        playingFieldCells.getY().get(i),
                        image
                );

                log.debug("Установка результата во вью : x - {} , y - {} , img - {}",
                        playingFieldCells.getX().get(i),
                        playingFieldCells.getY().get(i),
                        image);
            }
        }
    }

    @Override
    public void flagPlaning(int col, int row, boolean flag) {
        if (flag) {
            mainWindow.setCellImage(row, col, GameImage.MARKED);
        } else {
            mainWindow.setCellImage(row, col, GameImage.CLOSED);
        }
    }

    @Override
    public void updateBombCount(int count) {
        mainWindow.setBombsCount(count);
    }

    @Override
    public void timeUpdate(int time) {
        mainWindow.setTimerValue(time);
    }

    @Override
    public void updateGame(GameType gameType) {
        mainWindow.createGameField(gameType.rows, gameType.cols);
    }
}
