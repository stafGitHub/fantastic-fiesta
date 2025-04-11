package ru.shift.view;

import ru.shift.model.GameType;
import ru.shift.model.dto.ClickResult;


public interface ViewModelListener {
    void updateTheCellView(ClickResult clickResult);
    void flagPlaning(int row , int col , boolean flag);
    void updateBombCount(int count);
    void updateGame(GameType gameType);
    void loseGame();
    void winGame();
    void timeUpdate(int time);
    void updateRecord(int time);

}
