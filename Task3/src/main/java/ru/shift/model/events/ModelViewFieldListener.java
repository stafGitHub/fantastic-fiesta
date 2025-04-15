package ru.shift.model.events;

import ru.shift.model.GameType;
import ru.shift.model.dto.PlayingFieldCells;

public interface ModelViewFieldListener {
    void updateTheCellView(PlayingFieldCells playingFieldCells);
    void flagPlaning(int row , int col , boolean flag);
    void updateBombCount(int count);
    void timeUpdate(int time);
    void updateGame(GameType gameType);
}
