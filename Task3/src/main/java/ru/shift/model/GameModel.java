package ru.shift.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.FieldListener;
import ru.shift.controller.listeners.NewGameListener;
import ru.shift.controller.listeners.SettingsListeners;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.events.fields.FlagPlaning;
import ru.shift.events.fields.UpdateBombCount;
import ru.shift.events.fields.UpdateGame;
import ru.shift.events.fields.UpdateTheCell;
import ru.shift.events.game.result.Lose;
import ru.shift.events.game.result.Won;
import ru.shift.events.game.status.FirstClick;
import ru.shift.events.game.status.GameOver;
import ru.shift.events.game.status.NewGame;
import ru.shift.model.dto.Cell;
import ru.shift.model.dto.CellOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.shift.model.dto.Cell.EMPTY_COLUMN;
import static ru.shift.model.dto.Cell.MINE;

@Slf4j
public class GameModel implements
        FieldListener,
        SettingsListeners,
        NewGameListener,
        Publisher {
    private final Random RANDOM = new Random();

    private final List<Observer> observers = new ArrayList<>();
    private Cell[][] cells;
    private GameType gameType;
    private int openCellsToWin;
    private boolean gameOver;
    private boolean gameWon;
    private int openCells;
    private int mines;
    private boolean firstClick = true;


    public GameModel(GameType gameType) {
        createGame(gameType);
    }

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        observers.forEach(listener -> listener.onGameEvent(gameEvent));
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void openCell(int row, int col) {
        log.info("openCellLeftButton");
        if (gameOver || gameWon || cells[row][col].isFlag() || cells[row][col].isOpen()) {
            return;
        }

        var cellOutput = new CellOutput();

        if (firstClick) {
            createField(row, col);
            notifyListeners(new FirstClick());
            //Показывает все мины при первом клике
//            revealAllMines(cellOutput);
            firstClick = false;
        }

//         Можно закомитить , поражение отключиться
        if (checkGameOver(col, row)) {
            revealAllMines(cellOutput);
            updateTheCellView(cellOutput);
            gameOver();
            return;
        }

        log.info("Рекурсивное открытие");
        openNeighbors(col, row, cellOutput);
        log.info("Рекурсивное открытие клеток завершено");


        updateTheCellView(cellOutput);

        checkGameWinner();
    }

    @Override
    public void openAround(int row, int col) {
        log.info("openCellWithMouseCell");
        if (gameOver || gameWon || cells[row][col].isFlag()) {
            return;
        }

        var cellOutput = new CellOutput();
        var flags = countFlag(row, col);
        if (flags < cells[row][col].getMeaning() || (cells[row][col].getMeaning() == EMPTY_COLUMN && cells[row][col].isOpen()) || !cells[row][col].isOpen()) {
            return;
        }

        readingCellsBySquareCoordinates(row, col, cellOutput);
        updateTheCellView(cellOutput);

//         Можно закомитить , поражение отключиться
        if (!checkingForBombs(cellOutput)) {
            revealAllMines(cellOutput);
            updateTheCellView(cellOutput);
            gameOver();
            return;
        }

        checkGameWinner();
    }

    @Override
    public void flagPlaning(int row, int col) {

        if (cells[row][col].isFlag()) {
            cells[row][col].setFlag(false);
            notifyListeners(new FlagPlaning(row, col, false));
            mines++;
            updateBomb();

            return;
        }

        if (cells[row][col].isOpen() || mines == 0) {
            return;
        }

        if (!cells[row][col].isFlag()) {
            cells[row][col].setFlag(true);
            notifyListeners(new FlagPlaning(row, col, true));
            mines--;
            updateBomb();
        }
    }

    @Override
    public void changeDifficulty(GameType gameType) {
        log.info("Изменение сложности {}", gameType);
        createGame(gameType);
        notifyListeners(new UpdateGame(gameType));
    }

    @Override
    public void newGame() {
        createGame(gameType);
        notifyListeners(new UpdateGame(gameType));
    }

    private void readingCellsBySquareCoordinates(int row, int col, CellOutput cellOutput) {
        log.info("Чтение клеток вокруг координаты");
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (r == row && c == col || cells[r][c].isFlag() || cells[r][c].isOpen()) {
                    continue;
                }


                cellOutput.getX().add(c);
                cellOutput.getY().add(r);
                cellOutput.getColumnRes().add(cells[r][c].getMeaning());

                if (cells[r][c].getMeaning() == EMPTY_COLUMN) {
                    openNeighbors(c, r, cellOutput);
                } else {
                    openCells++;
                }


                cells[r][c].setOpen(true);

            }
        }
        log.info("Чтение клеток завершено");
    }

    private boolean checkingForBombs(CellOutput cellOutput) {
        log.info("Проверка на наличие бомб {}", cellOutput);
        var count = cellOutput.getColumnRes().stream()
                .filter(f -> f == MINE)
                .count();

        return count == 0;

    }

    private void revealAllMines(CellOutput cellOutput) {
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

    private int countFlag(int row, int col) {
        log.info("Чтение флагов вокруг координаты");
        int flag = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (cells[r][c].isFlag()) {
                    flag++;
                }
            }
        }
        log.info("Чтение флагов завершено");

        return flag;
    }

    private void openNeighbors(int col, int row, CellOutput cellOutput) {
        log.debug("openNeighbors : {} {}", col, row);
        if (col < 0 || col >= gameType.cols || row < 0 || row >= gameType.rows || cells[row][col].isOpen()) {
            return;
        }
        if (cells[row][col].isFlag()) {
            cells[row][col].setFlag(false);
            mines++;
            updateBomb();
        }

        cellOutput.getX().add(col);
        cellOutput.getY().add(row);
        cellOutput.getColumnRes().add(cells[row][col].getMeaning());

        int cellValue = cells[row][col].getMeaning();
        cells[row][col].setOpen(true);
        openCells++;

        if (cellValue != EMPTY_COLUMN) {
            return;
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                openNeighbors(col + dx, row + dy, cellOutput);
            }
        }
    }

    private boolean checkGameOver(int col, int row) {
        if (cells[row][col].getMeaning() == MINE) {
            gameOver = true;
            notifyListeners(new GameOver());
            return true;
        } else {
            return false;
        }
    }

    private void checkGameWinner() {
        if (openCells == openCellsToWin) {
            gameWon = true;
            notifyListeners(new GameOver());
            gameWon();
        }
    }

    private void gameOver() {
        log.info("Game over");
        gameOver = true;
        notifyListeners(new Lose());
    }

    private void gameWon() {
        log.info("Game won");
        gameWon = true;
        notifyListeners(new Won(gameType));
    }

    private void updateTheCellView(CellOutput cellOutput) {
        notifyListeners(new UpdateTheCell(cellOutput));
    }

    private void createField(int rows, int cols) {
        log.info("Создание игрового поля");
        fillTheFieldWithBombs(rows, cols);
        fillTheFieldWithNumbers();
        updateBomb();
    }

    private void fillTheFieldWithBombs(int row, int col) {
        log.info("заполнение игрового поля бомбами");

        int bomb = 0;
        while (bomb != gameType.numberOfBombs) {
            int xCoordinate = RANDOM.nextInt(gameType.rows);
            int yCoordinate = RANDOM.nextInt(gameType.cols);

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

    private void fillTheFieldWithNumbers() {
        log.info("Заполнение поля числами");

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                if (cells[row][col].getMeaning() != MINE) {
                    cells[row][col].setMeaning(countAdjacentMine(row, col));
                }
            }
        }

        log.info("Поле заполнено цифрами");
    }

    private int countAdjacentMine(int row, int col) {
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

    private void updateBomb() {
        log.info("Обновление view - количество бомб равно : {}", mines);
        notifyListeners(new UpdateBombCount(mines));
    }

    private void createGame(GameType gameType) {
        log.info("Создание новой игры : {}", gameType);
        notifyListeners(new NewGame());
        this.gameType = gameType;
        openCellsToWin = gameType.rows * gameType.cols - gameType.numberOfBombs;
        openCells = 0;
        gameWon = false;
        gameOver = false;
        fillThePlayingFieldWithCells();
        mines = gameType.numberOfBombs;
        firstClick = true;
        log.info("Создание игры завершено {} : ", gameType);
    }

    private void fillThePlayingFieldWithCells() {
        cells = new Cell[gameType.rows][gameType.cols];

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }
}