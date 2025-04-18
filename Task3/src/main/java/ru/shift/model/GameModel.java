package ru.shift.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.FieldListener;
import ru.shift.controller.listeners.NewGameListener;
import ru.shift.controller.listeners.SettingsListeners;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.counter.GameCounters;
import ru.shift.model.dto.CellOutput;
import ru.shift.model.events.UpdateTheCell;
import ru.shift.model.events.fields.FlagPlaning;
import ru.shift.model.events.fields.UpdateBombCount;
import ru.shift.model.events.fields.UpdateGame;
import ru.shift.model.events.game.result.Lose;
import ru.shift.model.events.game.result.Won;
import ru.shift.model.events.game.status.FirstClick;
import ru.shift.model.events.game.status.GameOver;
import ru.shift.model.events.game.status.NewGame;
import ru.shift.model.filed.PlayingField;

import java.util.ArrayList;
import java.util.List;

import static ru.shift.model.dto.Cell.EMPTY_COLUMN;
import static ru.shift.model.dto.Cell.MINE;

@Slf4j
public class GameModel implements
        FieldListener,
        SettingsListeners,
        NewGameListener,
        Publisher {
    private final List<Observer> observers = new ArrayList<>();
    private final PlayingField playingField;
    private final GameCounters gameCounters;
    private GameType gameType;
    private GameState gameState;
    private boolean firstOpenCell = true;

    public GameModel(GameType gameType ,
                     PlayingField playingField,
                     GameCounters gameCounters) {
        this.playingField = playingField;
        this.gameCounters = gameCounters;
        createGame(gameType);
    }

    @Override
    public void openCell(int row, int col) {
        log.info("openCellLeftButton");
        if (gameState ==GameState.WIN || gameState == GameState.LOSE || playingField.getCells()[row][col].isFlag()) {
            return;
        }

        var cellOutput = new ArrayList<CellOutput>();

        if (firstOpenCell) {
            playingField.fillInThField(row, col);
            updateBomb();
            notifyListeners(new FirstClick());
            firstOpenCell = false;
        }

        if (checkGameOver(col, row)) {
            gameOver();
            return;
        }

        log.info("Рекурсивное открытие");
        openNeighbors(col, row, cellOutput);
        log.info("Рекурсивное открытие клеток завершено");


        updateTheCellView(cellOutput);

        if (checkGameWinner()){
            gameWon();
        }
    }

    @Override
    public void openAround(int row, int col) {
        log.info("openCellWithMouseCell");
        if (gameState ==GameState.WIN || gameState == GameState.LOSE || playingField.getCells()[row][col].isFlag()) {
            return;
        }

        var cellOutput = new ArrayList<CellOutput>();
        var flags = countFlag(row, col);

        if (flags < playingField.getCells()[row][col].getMeaning() ||
                (playingField.getCells()[row][col].getMeaning() == EMPTY_COLUMN && playingField.getCells()[row][col].isOpen()) ||
                !playingField.getCells()[row][col].isOpen()) {
            return;
        }

        readingCellsBySquareCoordinates(row, col, cellOutput);
        updateTheCellView(cellOutput);

        if (!checkingForBombs(cellOutput)) {
            gameOver();
            return;
        }

        if (checkGameWinner()){
            gameWon();
        }
    }

    @Override
    public void flagPlaning(int row, int col) {
        if (playingField.getCells()[row][col].isFlag()) {
            playingField.getCells()[row][col].setFlag(false);
            notifyListeners(new FlagPlaning(row, col, false));
            gameCounters.incrementRemainingFlags();
            updateBomb();
        }else {
            if (playingField.getCells()[row][col].isOpen() || gameCounters.getRemainingFlags() == 0) {
                return;
            }
            playingField.getCells()[row][col].setFlag(true);
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

    private void readingCellsBySquareCoordinates(int row, int col, List<CellOutput> cellOutput) {
        log.info("Чтение клеток вокруг координаты");
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (r == row && c == col || playingField.getCells()[r][c].isFlag() || playingField.getCells()[r][c].isOpen()) {
                    continue;
                }


                cellOutput.add(new CellOutput(c,r,playingField.getCells()[r][c].getMeaning()));

                if (playingField.getCells()[r][c].getMeaning() == EMPTY_COLUMN) {
                    openNeighbors(c, r, cellOutput);
                } else {
                    gameCounters.incrementOpenCells();
                }


                playingField.getCells()[r][c].setOpen(true);

            }
        }
        log.info("Чтение клеток завершено");
    }

    private boolean checkingForBombs(List<CellOutput> cellOutput) {
        log.info("Проверка на наличие бомб {}", cellOutput);
        var count = cellOutput.stream()
                .filter(cell -> cell.number() == MINE)
                .count();

        return count == 0;

    }

    private int countFlag(int row, int col) {
        log.info("Чтение флагов вокруг координаты");
        int flag = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (playingField.getCells()[r][c].isFlag()) {
                    flag++;
                }
            }
        }
        log.info("Чтение флагов завершено");

        return flag;
    }

    private void openNeighbors(int col, int row, List<CellOutput> cellOutput) {
        log.debug("openNeighbors : {} {}", col, row);
        if (col < 0 || col >= gameType.cols || row < 0 || row >= gameType.rows || playingField.getCells()[row][col].isOpen()) {
            return;
        }

        if (playingField.getCells()[row][col].isFlag()) {
            playingField.getCells()[row][col].setFlag(false);
            gameCounters.incrementRemainingFlags();
            updateBomb();
        }

        cellOutput.add(new CellOutput(col,row,playingField.getCells()[row][col].getMeaning()));

        int cellValue = playingField.getCells()[row][col].getMeaning();
        playingField.getCells()[row][col].setOpen(true);
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
        if (playingField.getCells()[row][col].getMeaning() == MINE) {
            notifyListeners(new GameOver());
            return true;
        } else {
            return false;
        }
    }

    private boolean checkGameWinner() {
        if (gameCounters.getOpenCells() == gameCounters.getOpenCellsToWin()) {
            notifyListeners(new GameOver());
            return true;
        }else {
            return false;
        }
    }

    private void gameOver() {
        log.info("Game over");
        gameState = GameState.LOSE;
        notifyListeners(new Lose());
    }

    private void gameWon() {
        log.info("Game won");
        gameState = GameState.WIN;
        notifyListeners(new Won(gameType));
    }

    private void updateTheCellView(List<CellOutput> cellOutput) {
        notifyListeners(new UpdateTheCell(cellOutput));
    }

    private void updateBomb() {
        log.info("Обновление view - количество бомб равно : {}", gameCounters.getRemainingFlags());
        notifyListeners(new UpdateBombCount(gameCounters.getRemainingFlags()));
    }

    private void createGame(GameType gameType) {
        log.info("Создание новой игры : {}", gameType);

        notifyListeners(new NewGame());
        playingField.createField(gameType);
        gameCounters.createCounters(gameType);

        this.gameType = gameType;
        gameState = null;
        firstOpenCell = true;

        log.info("Создание игры завершено {} : ", gameType);
    }
}