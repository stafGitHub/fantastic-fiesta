package ru.shift.view.factory;

import ru.shift.model.dto.Cell;
import ru.shift.model.dto.CellOutput;
import ru.shift.view.GameImage;

public class ImageFactory {

    private ImageFactory() {
        throw new IllegalStateException("Static class");
    }

    public static GameImage getImageForCell(CellOutput cell) {

        if (cell.mine()) {
            return GameImage.BOMB;
        }

        if (cell.meaning() == Cell.EMPTY_COLUMN) {
            return GameImage.EMPTY;
        }

        return switch (cell.meaning()) {
            case 1 -> GameImage.NUM_1;
            case 2 -> GameImage.NUM_2;
            case 3 -> GameImage.NUM_3;
            case 4 -> GameImage.NUM_4;
            case 5 -> GameImage.NUM_5;
            case 6 -> GameImage.NUM_6;
            case 7 -> GameImage.NUM_7;
            case 8 -> GameImage.NUM_8;
            default -> throw new IllegalStateException("Unexpected value: " + cell.meaning());
        };
    }
}