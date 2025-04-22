package ru.shift.model.filed;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.model.GameType;
import ru.shift.model.dto.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
public class GameField {
    @Getter
    private Cell[][] cells;
    private final Random random = new Random();
    private GameType gameType;

    public void fillInThField(int rows, int cols) {
        log.info("Создание игрового поля");
        fillTheFieldWithBombs(rows, cols);
        fillTheFieldWithNumbers();
    }


    public List<Cell> getField() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList();
    }

    public void createField(GameType gameType) {
        this.gameType = gameType;
        initCells();
    }

    private void initCells() {
        cells = new Cell[gameType.rows][gameType.cols];

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    private int countAdjacentMine(int row, int col) {
        log.debug("Расчёт мин вокруг ячейки");
        int mineCount = 0;

        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (cells[r][c].isMine()) mineCount++;
            }
        }

        log.debug("Мин вокруг ячейки - {}", mineCount);
        return mineCount;
    }

    private void fillTheFieldWithBombs(int row, int col) {
        log.info("заполнение игрового поля бомбами");

        int bomb = 0;
        while (bomb != gameType.numberOfBombs) {
            int xCoordinate = random.nextInt(gameType.rows);
            int yCoordinate = random.nextInt(gameType.cols);

            if (xCoordinate == row && yCoordinate == col) {
                continue;
            }

            if (!cells[xCoordinate][yCoordinate].isMine()) {
                cells[xCoordinate][yCoordinate].setMine(true);
                bomb++;
            }
        }

        log.info("Заполнение завершено успешно");
    }

    private void fillTheFieldWithNumbers() {
        log.info("Заполнение поля числами");

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                if (!cells[row][col].isMine()) {
                    cells[row][col].setMeaning(countAdjacentMine(row, col));
                }
            }
        }

        log.info("Поле заполнено цифрами");
    }
}
