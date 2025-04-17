package ru.shift.model.state;

import lombok.extern.slf4j.Slf4j;
import ru.shift.model.GameType;
import ru.shift.model.dto.Cell;
import ru.shift.model.dto.CellOutput;

import java.util.Random;

import static ru.shift.model.dto.Cell.MINE;

@Slf4j
public class PlayingField {
    private Cell[][] cells;
    private final int openCellsToWin;
    private int openCells = 0;
    private int mines;
    private final Random random = new Random();

    public PlayingField(GameType gameType) {
        this.mines = gameType.numberOfBombs;
        this.openCellsToWin = gameType.cols * gameType.rows - gameType.numberOfBombs;
    }

    public void incrementMines() {
        this.mines++;
    }

    public void decrementMines() {
        this.mines--;
    }

    public void incrementOpenCells() {
        this.openCells++;
    }

    public void createField(int rows, int cols , GameType gameType) {
        log.info("Создание игрового поля");
        fillThePlayingFieldWithCells(gameType);
        fillTheFieldWithBombs(rows, cols, gameType);
        fillTheFieldWithNumbers(gameType);
    }

    public void revealAllMines(CellOutput cellOutput , GameType gameType) {
        log.info("Открытие всех мин");

        for (int r = 0; r < gameType.rows; r++) {
            for (int c = 0; c < gameType.cols; c++) {
                if (cells[r][c].getMeaning() == MINE && !cells[r][c].isFlag()) {
                    cellOutput.getX().add(c);
                    cellOutput.getY().add(r);
                    cellOutput.getColumnRes().add(MINE);
                }
            }
        }

        log.info("Открытие мин завершено");
    }

    private void fillTheFieldWithBombs(int row, int col, GameType gameType) {
        log.info("заполнение игрового поля бомбами");

        int bomb = 0;
        while (bomb != gameType.numberOfBombs) {
            int xCoordinate = random.nextInt(gameType.rows);
            int yCoordinate = random.nextInt(gameType.cols);

            if (xCoordinate == row && yCoordinate == col) {
                continue;
            }

            if (cells[xCoordinate][yCoordinate].getMeaning() != MINE) {
                cells[xCoordinate][yCoordinate].setMeaning(MINE);
                bomb++;
            }
        }

        log.info("Заполнение завершено успешно");
    }

    private void fillTheFieldWithNumbers(GameType gameType) {
        log.info("Заполнение поля числами");

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                if (cells[row][col].getMeaning() != MINE) {
                    cells[row][col].setMeaning(countAdjacentMine(row, col, gameType));
                }
            }
        }

        log.info("Поле заполнено цифрами");
    }

    private int countAdjacentMine(int row, int col, GameType gameType) {
        log.debug("Расчёт мин вокруг ячейки");
        int mineCount = 0;

        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (cells[r][c].getMeaning() == MINE) mineCount++;
            }
        }

        log.debug("Мин вокруг ячейки - {}", mineCount);
        return mineCount;
    }

    private void fillThePlayingFieldWithCells(GameType gameType) {
        cells = new Cell[gameType.rows][gameType.cols];

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }
}
