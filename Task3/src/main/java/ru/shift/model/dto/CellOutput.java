package ru.shift.model.dto;

/**
 * Данные для обновления View, обновляется не всё поле, а только ячейки которые были использованны
 */
public record CellOutput(int x , int y , int number) {
}
