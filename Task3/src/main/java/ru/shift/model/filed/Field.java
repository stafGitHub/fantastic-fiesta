package ru.shift.model.filed;

import ru.shift.model.dto.Cell;
import ru.shift.model.dto.CellOutput;

public interface Field {
    Cell[][] getCells();
    CellOutput revealAllMines(CellOutput cellOutput);
}
