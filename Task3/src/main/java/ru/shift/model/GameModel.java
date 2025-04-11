package ru.shift.model;

import ru.shift.model.listners.ControllerModelListener;
import ru.shift.model.dto.ClickResult;
import ru.shift.model.listners.ViewModelListener;
import ru.shift.model.records.RecordManager;

import java.util.Random;

public class GameModel implements ControllerModelListener {
    private final ViewModelListener viewModelListener;
    private final RecordManager recordManager;

    private final static int OPEN_COLUMN = -2;
    private final static int MINE = -1;
    private final static int EMPTY_COLUMN = 0;

    private GameType gameType;
    private int openCellsToWin;

    private int[][] mineField;
    private boolean[][] flags;
    private final Timer timer;

    private boolean gameOver;
    private boolean gameWon;

    private int openCells;
    private int mines;

    private boolean firstClick = true;

    private final Random RANDOM = new Random();

    public GameModel(ViewModelListener viewModelListener, GameType gameType, RecordManager recordManager, Timer timer) {
        this.viewModelListener = viewModelListener;
        this.recordManager = recordManager;
        this.timer = timer;
        createGame(gameType);

    }

    @Override
    public void openCellLeftButton(int row , int col) {
        var clickResult = new ClickResult();

        if (firstClick){
            createField();
            timer.start();
            firstClick = false;
        }


        if (flags[row][col]) {
            return;
        }

//        if (checkGameOver(col, row)){
//            return;
//        }

        openNeighbors(col, row, clickResult);
        updateTheCellView(clickResult);

        checkGameWinner();
    }

    @Override
    public void flagPlaning(int row , int col) {
        if (mineField[row][col] == OPEN_COLUMN || mines == 0) {
            return;
        }
        if (!flags[row][col]) {
            flags[row][col] = true;
            viewModelListener.flagPlaning(row,col , true);
            mines--;
            updateBomb();
        }else {
            flags[row][col] = false;
            viewModelListener.flagPlaning(row,col , false);
            mines++;
            updateBomb();
        }
    }

    @Override
    public void changeDifficulty(GameType gameType) {
        createGame(gameType);
        viewModelListener.updateGame(gameType);
    }

    @Override
    public void newGame() {
        createGame(gameType);
        viewModelListener.updateGame(gameType);
    }

    private void openNeighbors(int col, int row, ClickResult clickResult) {
        if (col < 0 || col >= gameType.cols || row < 0 || row >= gameType.rows ||
                mineField[row][col] == OPEN_COLUMN) {
            return;
        }
        if (flags[row][col]) {
            flags[row][col] = false;
            mines++;
            updateBomb();
        }

        clickResult.getX().add(col);
        clickResult.getY().add(row);
        clickResult.getColumnRes().add(mineField[row][col]);

        int cellValue = mineField[row][col];
        mineField[row][col] = OPEN_COLUMN;
        openCells++;

        if (cellValue != EMPTY_COLUMN) {
            return;
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                openNeighbors(col + dx, row + dy, clickResult);
            }
        }
    }

    private boolean checkGameOver(int col, int row) {
        if (mineField[row][col] == MINE) {
            gameOver = true;
            timer.stop();
            viewModelListener.loseGame();
        }
        return false;
    }

    private void checkGameWinner() {
        if (openCells == openCellsToWin) {
            timer.stop();
            gameWon = true;
            var record = recordManager.checkRecords(timer.getSecondsPassed().get(), gameType);

            if (record){
                viewModelListener.updateRecord(timer.getSecondsPassed().get());
                recordManager.updateRecord(timer.getSecondsPassed().get());
            }

            viewModelListener.winGame();
        }
    }

    private void updateTheCellView(ClickResult clickResult) {
        viewModelListener.updateTheCellView(clickResult);
    }

    private void createField(){
        fillTheFieldWithBombs(gameType.rows, gameType.cols);
        fillTheFieldWithNumbers();
        updateBomb();
    }

    private void fillTheFieldWithBombs(int col, int row) {
        int bomb = 0;
        while (bomb != gameType.numberOfBombs) {
            int xCoordinate = RANDOM.nextInt(gameType.rows);
            int yCoordinate = RANDOM.nextInt(gameType.cols);

            if (xCoordinate == row && yCoordinate == col) {
                continue;
            }

            if (mineField[xCoordinate][yCoordinate] != MINE) {
                mineField[xCoordinate][yCoordinate] = MINE;
                bomb++;
            }
        }
    }

    private void fillTheFieldWithNumbers() {
        for (int row = 0; row < gameType.rows; row++) {
            for (int col = 0; col < gameType.cols; col++) {

                if (mineField[row][col] != MINE) {
                    int mineCount = 0;
                    for (int r = Math.max(0, row - 1); r <= Math.min(gameType.rows - 1, row + 1); r++) {
                        for (int c = Math.max(0, col - 1); c <= Math.min(gameType.cols - 1, col + 1); c++) {

                            if (mineField[r][c] == MINE) {
                                mineCount++;
                            }

                        }
                    }
                    mineField[row][col] = mineCount;
                }

            }
        }
    }

    private void updateBomb(){
        viewModelListener.updateBombCount(mines);
    }

    private void createGame(GameType gameType){
        this.gameType = gameType;
        openCellsToWin = gameType.rows * gameType.cols - gameType.numberOfBombs;
        openCells = 0;

        timer.reset();

        mineField = new int[gameType.rows][gameType.cols];
        flags = new boolean[gameType.rows][gameType.cols];
        mines = gameType.numberOfBombs;
        firstClick = true;
    }
}