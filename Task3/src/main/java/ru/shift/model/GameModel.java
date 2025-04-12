package ru.shift.model;

import ru.shift.controller.listeners.ControllerModelFieldListener;
import ru.shift.controller.listeners.ControllerModelNewGameListener;
import ru.shift.controller.listeners.ControllerModelSettingsListeners;
import ru.shift.model.dto.PlayingFieldCells;
import ru.shift.model.listeners.ModelViewFieldListener;
import ru.shift.model.listeners.ModelViewGameResultListener;
import ru.shift.model.listeners.ModelViewRecordListener;
import ru.shift.model.records.RecordManager;

import java.util.Random;

public class GameModel implements
        ControllerModelFieldListener,
        ControllerModelSettingsListeners,
        ControllerModelNewGameListener {

    private final ModelViewFieldListener modelViewFieldListener;
    private final ModelViewGameResultListener modelViewGameResultListener;
    private final ModelViewRecordListener modelViewRecordListener;
    private final RecordManager recordManager;

    private final static int MINE = -1;
    private final static int EMPTY_COLUMN = 0;

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
        if (gameOver || gameWon || flags[row][col]) {
            return;
        }

        var clickResult = new PlayingFieldCells();

        if (firstClick) {
            createField(row, col);
            timer.start();
            firstClick = false;
        }

        if (checkGameOver(col, row)) {
            revealAllMines(clickResult);
            updateTheCellView(clickResult);
            gameOver();
            return;
        }

        openNeighbors(col, row, clickResult);
        updateTheCellView(clickResult);

        checkGameWinner();
    }

    @Override
    public void openCellWithMouseCell(int row, int col) {
        if (gameOver || gameWon || flags[row][col]) {
            return;
        }
        var clickResult = new PlayingFieldCells();
        var flags = countFlag(row, col);
        if (flags < fields[row][col] || (fields[row][col] == EMPTY_COLUMN && openFields[row][col]) || !openFields[row][col]) {
            return;
        }

        readingCellsInCrudCoordinates(row, col, clickResult);
        updateTheCellView(clickResult);

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
        if (openFields[row][col] || mines == 0) {
            return;
        }
        if (!flags[row][col]) {
            flags[row][col] = true;
            modelViewFieldListener.flagPlaning(row, col, true);
            mines--;
            updateBomb();
        } else {
            flags[row][col] = false;
            modelViewFieldListener.flagPlaning(row, col, false);
            mines++;
            updateBomb();
        }
    }

    @Override
    public void changeDifficulty(GameType gameType) {
        createGame(gameType);
        modelViewFieldListener.updateGame(gameType);
    }

    @Override
    public void newGame() {
        createGame(gameType);
        modelViewFieldListener.updateGame(gameType);
    }

    private void readingCellsInCrudCoordinates(int row, int col, PlayingFieldCells playingFieldCells) {
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (r == row && c == col || flags[r][c] || openFields[r][c]) {
                    continue;
                }
                playingFieldCells.getX().add(c);
                playingFieldCells.getY().add(r);
                playingFieldCells.getColumnRes().add(fields[r][c]);
                openCells++;
                openFields[r][c] = true;
            }
        }
    }

    private boolean checkingForBombs(PlayingFieldCells playingFieldCells) {
        var count = playingFieldCells.getColumnRes().stream()
                .filter(f -> f == MINE)
                .count();

        return count == 0;

    }

    private void revealAllMines(PlayingFieldCells playingFieldCells) {
        for (int r = 0; r < gameType.rows; r++) {
            for (int c = 0; c < gameType.cols; c++) {
                if (fields[r][c] == MINE && !flags[r][c]) {
                    playingFieldCells.getX().add(c);
                    playingFieldCells.getY().add(r);
                    playingFieldCells.getColumnRes().add(MINE);
                }
            }
        }
    }

    private int countFlag(int row, int col) {
        int flag = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {
                if (flags[r][c]) {
                    flag++;
                }
            }
        }
        return flag;
    }

    private void openNeighbors(int col, int row, PlayingFieldCells playingFieldCells) {
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
        fillTheFieldWithBombs(rows, cols);
        fillTheFieldWithNumbers();
        updateBomb();
    }

    private void fillTheFieldWithBombs(int row, int col) {
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
    }

    private void fillTheFieldWithNumbers() {
        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {
                if (fields[row][col] != MINE) {
                    fields[row][col] = countAdjacentMines(row, col);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int mineCount = 0;
        for (int r = Math.max(0, row-1); r <= Math.min(gameType.rows-1, row+1); r++) {
            for (int c = Math.max(0, col-1); c <= Math.min(gameType.cols-1, col+1); c++) {
                if (fields[r][c] == MINE) mineCount++;
            }
        }
        return mineCount;
    }

    private void updateBomb() {
        modelViewFieldListener.updateBombCount(mines);
    }

    private void createGame(GameType gameType) {
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
    }
}