package ru.shift.view;

import ru.shift.model.GameType;
import ru.shift.model.dto.PlayingFieldCells;


public interface ViewModelListener {
    void updateTheCellView(PlayingFieldCells playingFieldCells);
    void flagPlaning(int row , int col , boolean flag);
    void updateBombCount(int count);
    void timeUpdate(int time);
    void updateGame(GameType gameType);

    void loseGame();
    void winGame();

    void updateRecord(int time);

}
