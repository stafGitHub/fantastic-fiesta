package ru.shift.model.dto;

import java.util.List;

public record CellOutput(int x, int y, int meaning, boolean flag, boolean open, boolean mine) {
    public static CellOutput from(Cell cell) {
        return new CellOutput(
                cell.getX(),
                cell.getY(),
                cell.getMeaning(),
                cell.isFlag(),
                cell.isOpen(),
                cell.isMine()
        );
    }

    public static List<CellOutput> from(List<Cell> cells) {
        return cells.stream().map(CellOutput::from).toList();
    }
}
