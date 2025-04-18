package ru.shift.model;

import lombok.Getter;

@Getter
public class GameCounters {
    private int openCellsToWin;
    private int openCells;
    private int remainingFlags;

    public void createCounters(GameType gameType) {
        this.openCellsToWin = gameType.cols * gameType.rows - gameType.numberOfBombs;
        this.remainingFlags = gameType.numberOfBombs;
        this.openCells = 0;
    }

    public void incrementOpenCells() {
        openCells++;
    }

    public void incrementRemainingFlags() {
        remainingFlags++;
    }

    public void decrementRemainingFlags() {
        remainingFlags--;
    }
}
