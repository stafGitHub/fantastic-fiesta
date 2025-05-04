package ru.shift.controller.listeners;

public interface FieldListener {
    void openCell(int row, int col);

    void openAround(int row, int col);

    void flagPlaning(int row, int col);
}
