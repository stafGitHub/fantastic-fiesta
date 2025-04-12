package ru.shift.view.observers;

import lombok.RequiredArgsConstructor;
import ru.shift.model.GameType;
import ru.shift.model.dto.PlayingFieldCells;
import ru.shift.model.listeners.ModelViewFieldListener;
import ru.shift.view.GameImage;
import ru.shift.view.windows.MainWindow;

@RequiredArgsConstructor
public class GameWindowsObserver implements ModelViewFieldListener {
    private final MainWindow mainWindow;

    @Override
    public void updateTheCellView(PlayingFieldCells playingFieldCells) {
        for (int i = 0; i < playingFieldCells.getColumnRes().size(); i++) {

            switch (playingFieldCells.getColumnRes().get(i)) {
                case -1 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.BOMB);
                case 0 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.EMPTY);
                case 1 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_1);
                case 2 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_2);
                case 3 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_3);
                case 4 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_4);
                case 5 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_5);
                case 6 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_6);
                case 7 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_7);
                case 8 ->
                        mainWindow.setCellImage(playingFieldCells.getX().get(i), playingFieldCells.getY().get(i), GameImage.NUM_8);
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
