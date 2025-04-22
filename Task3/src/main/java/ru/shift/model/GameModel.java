package ru.shift.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.FieldListener;
import ru.shift.controller.listeners.NewGameListener;
import ru.shift.controller.listeners.SettingsListeners;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.counter.GameCounters;
import ru.shift.model.dto.Cell;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.fields.FlagPlaning;
import ru.shift.model.events.fields.UpdateBombCount;
import ru.shift.model.events.fields.UpdateGame;
import ru.shift.model.events.fields.UpdateTheCell;
import ru.shift.model.events.game.result.GameResult;
import ru.shift.model.events.game.status.FirstClick;
import ru.shift.model.events.game.status.GameOver;
import ru.shift.model.events.game.status.NewGame;
import ru.shift.model.filed.GameField;

import java.util.ArrayList;
import java.util.List;

import static ru.shift.model.dto.Cell.EMPTY_COLUMN;

@Slf4j
public class GameModel implements
        FieldListener,
        SettingsListeners,
        NewGameListener,
        Publisher {
    private final List<Observer> observers = new ArrayList<>();
    private final GameField gameField;
    private final GameCounters gameCounters;
    private GameType gameType;
    private GameState gameState;
    private boolean firstOpenCell = true;

    public GameModel(GameType gameType,
                     GameField gameField,
                     GameCounters gameCounters) {
        this.gameField = gameField;
        this.gameCounters = gameCounters;
        createGame(gameType);
    }

    @Override
    public void openCell(int row, int col) {
        log.info("openCellLeftButton");
        if (gameState == GameState.WIN || gameState == GameState.LOSE || gameField.getCells()[row][col].isFlag()) {
            return;
        }

        var cellOutput = new ArrayList<Cell>();

        if (firstOpenCell) {
            gameField.fillInThField(row, col);
            updateBomb();
            notifyListeners(new FirstClick());
            firstOpenCell = false;
        }

        if (checkGameOver(col, row)) {
            gameLose();
            return;
        }

        log.info("Рекурсивное открытие");
        openNeighbors(col, row, cellOutput);
        log.info("Рекурсивное открытие клеток завершено");


        updateTheCellView(cellOutput);

        if (checkGameWinner()) {
            gameWon();
        }
    }

    @Override
    public void openAround(int row, int col) {
        log.info("openCellWithMouseCell");
        if (gameState == GameState.WIN || gameState == GameState.LOSE || gameField.getCells()[row][col].isFlag()) {
            return;
        }

        var cellOutput = new ArrayList<Cell>();
        var flags = countFlag(row, col);

        if (flags < gameField.getCells()[row][col].getMeaning() ||
                (gameField.getCells()[row][col].getMeaning() == EMPTY_COLUMN && gameField.getCells()[row][col].isOpen()) ||
                !gameField.getCells()[row][col].isOpen()) {
            return;
        }

        readingCellsBySquareCoordinates(row, col, cellOutput);
        updateTheCellView(cellOutput);

        if (!checkingForBombs(cellOutput)) {
            gameOver();
            gameLose();
            return;
        }

        if (checkGameWinner()) {
            gameWon();
        }
    }

    @Override
    public void flagPlaning(int row, int col) {
        if (gameField.getCells()[row][col].isFlag()) {
            gameField.getCells()[row][col].setFlag(false);
            notifyListeners(new FlagPlaning(row, col, false));
            gameCounters.incrementRemainingFlags();
            updateBomb();
        } else {
            if (gameField.getCells()[row][col].isOpen() || gameCounters.getRemainingFlags() == 0) {
                return;
            }
            gameField.getCells()[row][col].setFlag(true);
            notifyListeners(new FlagPlaning(row, col, true));
            gameCounters.decrementRemainingFlags();
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

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        observers.forEach(listener -> listener.onGameEvent(gameEvent));
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }

    private void readingCellsBySquareCoordinates(int row, int col, List<Cell> cellOutput) {
        log.info("Чтение клеток вокруг координаты");
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (r == row && c == col || gameField.getCells()[r][c].isFlag() || gameField.getCells()[r][c].isOpen()) {
                    continue;
                }


                cellOutput.add(gameField.getCells()[c][r]);

                if (gameField.getCells()[r][c].getMeaning() == EMPTY_COLUMN) {
                    openNeighbors(c, r, cellOutput);
                } else {
                    gameCounters.incrementOpenCells();
                }


                gameField.getCells()[r][c].setOpen(true);

            }
        }
        log.info("Чтение клеток завершено");
    }

    private boolean checkingForBombs(List<Cell> cells) {
        log.info("Проверка на наличие бомб {}", cells);
        var count = cells.stream()
                .filter(cell -> !cell.isFlag())
                .filter(Cell::isMine)
                .count();

        return count == 0;

    }

    private int countFlag(int row, int col) {
        log.info("Чтение флагов вокруг координаты");
        int flag = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (gameField.getCells()[r][c].isFlag()) {
                    flag++;
                }
            }
        }
        log.info("Чтение флагов завершено");

        return flag;
    }

    private void openNeighbors(int col, int row, List<Cell> cellOutput) {
        log.debug("openNeighbors : {} {}", col, row);
        if (col < 0 || col >= gameType.cols || row < 0 || row >= gameType.rows || gameField.getCells()[row][col].isOpen()) {
            return;
        }

        if (gameField.getCells()[row][col].isFlag()) {
            gameField.getCells()[row][col].setFlag(false);
            gameCounters.incrementRemainingFlags();
            updateBomb();
        }

        cellOutput.add(gameField.getCells()[row][col]);

        int cellValue = gameField.getCells()[row][col].getMeaning();
        gameField.getCells()[row][col].setOpen(true);
        gameCounters.incrementOpenCells();

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
        if (gameField.getCells()[row][col].isMine()) {
            gameOver();
            return true;
        } else {
            return false;
        }
    }


    private boolean checkGameWinner() {
        if (gameCounters.getOpenCells() == gameCounters.getOpenCellsToWin()) {
            gameOver();
            return true;
        } else {
            return false;
        }
    }

    private void gameOver() {
        notifyListeners(new GameOver());
    }

    private void gameLose() {
        log.info("Game over");
        gameState = GameState.LOSE;
        var gameResult = new GameResult(GameState.LOSE, gameField.getField(), gameType);
        notifyListeners(gameResult);
    }

    private void gameWon() {
        log.info("Game won");
        gameState = GameState.WIN;
        var gameResult = new GameResult(GameState.WIN, gameField.getField(), gameType);
        notifyListeners(gameResult);
    }

    private void updateTheCellView(List<Cell> cellOutput) {
        notifyListeners(new UpdateTheCell(cellOutput));
    }

    private void updateBomb() {
        log.info("Обновление view - количество бомб равно : {}", gameCounters.getRemainingFlags());
        notifyListeners(new UpdateBombCount(gameCounters.getRemainingFlags()));
    }

    private void createGame(GameType gameType) {
        log.info("Создание новой игры : {}", gameType);

        notifyListeners(new NewGame());
        gameField.createField(gameType);
        gameCounters.createCounters(gameType);

        this.gameType = gameType;
        gameState = null;
        firstOpenCell = true;

        log.info("Создание игры завершено {} : ", gameType);
    }
}