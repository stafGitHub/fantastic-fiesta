package ru.shift.model.listners;

import ru.shift.model.GameType;

public interface ControllerModelListener {
   void openCellLeftButton(int row, int col);
   void flagPlaning(int row, int col);
   void changeDifficulty(GameType gameType);
   void newGame();
}
