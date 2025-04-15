package ru.shift.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.controller.listeners.ControllerModelFieldListener;
import ru.shift.controller.listeners.ControllerModelNewGameListener;
import ru.shift.controller.listeners.ControllerModelSettingsListeners;
import ru.shift.model.dto.PlayingFieldCells;
import ru.shift.model.events.*;
import ru.shift.model.events.game.result.Win;
import ru.shift.model.records.RecordManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class GameModel implements
        ControllerModelFieldListener,
        ControllerModelSettingsListeners,
        ControllerModelNewGameListener , Publisher {
    public final static int MINE = -1;
    public final static int EMPTY_COLUMN = 0;

    private final ModelViewFieldListener modelViewFieldListener;
    private final ModelViewGameResultListener modelViewGameResultListener;
    private final ModelViewRecordListener modelViewRecordListener;
    private final RecordManager recordManager;

    private final List<GameSettingsListener> gameSettingsListeners = new ArrayList<>();

    @Override
    public void addListener(GameSettingsListener gameSettingsListener) {
        gameSettingsListeners.add(gameSettingsListener);
    }

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        gameSettingsListeners.forEach(listener -> listener.onGameEvent(gameEvent));
    }

    private GameType gameType;
    private int openCellsToWin;

    private int[][] fields;
    private boolean[][] flags;
    private boolean[][] openFields;
    private final Timer timer;

    private boolean gameOver;
    private boolean gameWon;

    private int openCells;
    private int mines;

    private boolean firstClick = true;

    private final Random RANDOM = new Random();

    public GameModel(GameType gameType,
                     RecordManager recordManager,
                     Timer timer,
                     ModelViewFieldListener modelViewFieldListener,
                     ModelViewGameResultListener modelViewGameResultListener,
                     ModelViewRecordListener modelViewRecordListener) {
        this.modelViewFieldListener = modelViewFieldListener;
        this.recordManager = recordManager;
        this.timer = timer;
        this.modelViewGameResultListener = modelViewGameResultListener;
        this.modelViewRecordListener = modelViewRecordListener;
        createGame(gameType);

    }

    @Override
    public void openCellLeftButton(int row, int col) {
        log.info("openCellLeftButton");
        if (gameOver || gameWon || flags[row][col] || openFields[row][col]) {
            return;
        }

        var clickResult = new PlayingFieldCells();

        if (firstClick) {
            createField(row, col);
            timer.start();
            firstClick = false;
        }

//         Можно закомитить , поражение отключиться
        if (checkGameOver(col, row)) {
            revealAllMines(clickResult);
            updateTheCellView(clickResult);
            gameOver();
            return;
        }

        log.info("Рекурсивное открытие");
        long startTime = System.nanoTime();

        openNeighbors(col, row, clickResult);

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        log.info("Рекурсивное открытие клеток завершено : {}", elapsedTime);


        updateTheCellView(clickResult);

        checkGameWinner();
    }

    @Override
    public void openCellWithMouseCell(int row, int col) {
        log.info("openCellWithMouseCell");
        if (gameOver || gameWon || flags[row][col]) {
            return;
        }

        var clickResult = new PlayingFieldCells();
        var flags = countFlag(row, col);
        if (flags < fields[row][col] || (fields[row][col] == EMPTY_COLUMN && openFields[row][col]) || !openFields[row][col]) {
            return;
        }

        readingCellsBySquareCoordinates(row, col, clickResult);
        updateTheCellView(clickResult);

//         Можно закомитить , поражение отключиться
        if (!checkingForBombs(clickResult)) {
            revealAllMines(clickResult);
            updateTheCellView(clickResult);
            gameOver();
            return;
        }

        checkGameWinner();
    }

    @Override
    public void flagPlaning(int row, int col) {

        if (flags[row][col]) {
            flags[row][col] = false;
            modelViewFieldListener.flagPlaning(row, col, false);
            mines++;
            updateBomb();

            return;
        }

        if (openFields[row][col] || mines == 0) {
            return;
        }

        if (!flags[row][col]) {
            flags[row][col] = true;
            modelViewFieldListener.flagPlaning(row, col, true);
            mines--;
            updateBomb();
        }
    }

    @Override
    public void changeDifficulty(GameType gameType) {
        log.info("Изменение сложности {}", gameType);
        createGame(gameType);
        modelViewFieldListener.updateGame(gameType);
    }

    @Override
    public void newGame() {
        createGame(gameType);
        modelViewFieldListener.updateGame(gameType);
    }

    private void readingCellsBySquareCoordinates(int row, int col, PlayingFieldCells playingFieldCells) {
        log.info("Чтение клеток вокруг координаты");
        long startTime = System.nanoTime();

        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (r == row && c == col || flags[r][c] || openFields[r][c]) {
                    continue;
                }


                playingFieldCells.getX().add(c);
                playingFieldCells.getY().add(r);
                playingFieldCells.getColumnRes().add(fields[r][c]);
                openCells++;

                if (fields[r][c] == EMPTY_COLUMN) {
                    openNeighbors(c, r, playingFieldCells);
                }
                openFields[r][c] = true;
            }
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        log.info("Чтение клеток завершено : {}", elapsedTime);
    }

    private boolean checkingForBombs(PlayingFieldCells playingFieldCells) {
        log.info("Проверка на наличие бомб {}", playingFieldCells);
        var count = playingFieldCells.getColumnRes().stream()
                .filter(f -> f == MINE)
                .count();

        return count == 0;

    }

    private void revealAllMines(PlayingFieldCells playingFieldCells) {
        log.info("Открытие всех мин");
        long startTime = System.nanoTime();

        for (int r = 0; r < gameType.rows; r++) {
            for (int c = 0; c < gameType.cols; c++) {
                if (fields[r][c] == MINE && !flags[r][c]) {
                    playingFieldCells.getX().add(c);
                    playingFieldCells.getY().add(r);
                    playingFieldCells.getColumnRes().add(MINE);
                }
            }
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        log.info("Открытие мин завершено : {}", elapsedTime);
    }

    private int countFlag(int row, int col) {
        log.info("Чтение флагов вокруг координаты");
        long startTime = System.nanoTime();
        int flag = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (flags[r][c]) {
                    flag++;
                }
            }
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        log.info("Чтение флагов завершено : {}", elapsedTime);

        return flag;
    }

    private void openNeighbors(int col, int row, PlayingFieldCells playingFieldCells) {
        log.debug("openNeighbors : {} {}", col, row);
        if (col < 0 || col >= gameType.cols || row < 0 || row >= gameType.rows || openFields[row][col]) {
            return;
        }
        if (flags[row][col]) {
            flags[row][col] = false;
            mines++;
            updateBomb();
        }

        playingFieldCells.getX().add(col);
        playingFieldCells.getY().add(row);
        playingFieldCells.getColumnRes().add(fields[row][col]);

        int cellValue = fields[row][col];
        openFields[row][col] = true;
        openCells++;

        if (cellValue != EMPTY_COLUMN) {
            return;
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                openNeighbors(col + dx, row + dy, playingFieldCells);
            }
        }
    }

    private boolean checkGameOver(int col, int row) {
        if (fields[row][col] == MINE) {
            gameOver = true;
            return true;
        } else {
            return false;
        }
    }

    private void gameOver() {
        log.info("Game over");
        gameOver = true;
        timer.stop();
        modelViewGameResultListener.loseGame();
    }

    private void checkGameWinner() {
        if (openCells == openCellsToWin) {
            gameWon = true;
            gameWon();
        }
    }

    private void gameWon() {
        log.info("Game won");
        timer.stop();
        gameWon = true;
        var record = recordManager.checkRecords(timer.getSecondsPassed().get(), gameType);

        if (record) {
            modelViewRecordListener.updateRecord(timer.getSecondsPassed().get());
            recordManager.updateRecord(timer.getSecondsPassed().get());
        }

        modelViewGameResultListener.winGame();
    }

    private void updateTheCellView(PlayingFieldCells playingFieldCells) {
        modelViewFieldListener.updateTheCellView(playingFieldCells);
    }

    private void createField(int rows, int cols) {
        log.info("Создание игрового поля");
        fillTheFieldWithBombs(rows, cols);
        fillTheFieldWithNumbers();
        updateBomb();
    }

    private void fillTheFieldWithBombs(int row, int col) {
        log.info("заполнение игрового поля бомбами");
        long startTime = System.nanoTime();

        int bomb = 0;
        while (bomb != gameType.numberOfBombs) {
            int xCoordinate = RANDOM.nextInt(gameType.rows);
            int yCoordinate = RANDOM.nextInt(gameType.cols);

            if (xCoordinate == row && yCoordinate == col) {
                continue;
            }

            if (fields[xCoordinate][yCoordinate] != MINE) {
                fields[xCoordinate][yCoordinate] = MINE;
                bomb++;
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        log.info("Заполнение завершено успешно : {}", elapsedTime);
    }

    private void fillTheFieldWithNumbers() {
        log.info("Заполнение поля числами");
        long startTime = System.nanoTime();

        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                if (fields[row][col] != MINE) {
                    fields[row][col] = countAdjacentMine(row, col);
                }
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        log.info("Поле заполнено цифрами: {}", elapsedTime);
    }

    private int countAdjacentMine(int row, int col) {
        log.debug("Расчёт мин вокруг ячейки");
        long startTime = System.nanoTime();

        int mineCount = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (fields[r][c] == MINE) mineCount++;
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        log.debug("Мин вокруг ячейки - {} : {}", mineCount, elapsedTime);
        return mineCount;
    }

    private void updateBomb() {
        log.info("Обновление view - количество бомб равно : {}", mines);
        modelViewFieldListener.updateBombCount(mines);
    }

    private void createGame(GameType gameType) {
        log.info("Создание новой игры : {}", gameType);
        this.gameType = gameType;
        openCellsToWin = gameType.rows * gameType.cols - gameType.numberOfBombs;
        openCells = 0;
        gameWon = false;
        gameOver = false;

        timer.reset();

        fields = new int[gameType.rows][gameType.cols];
        flags = new boolean[gameType.rows][gameType.cols];
        openFields = new boolean[gameType.rows][gameType.cols];
        mines = gameType.numberOfBombs;
        firstClick = true;
        log.info("Создание игры завершено {}", gameType);
    }
}