package ru.shift.model.dto;

import lombok.Getter;

import java.util.ArrayList;

/**
 * Данные для обновления View, обновляется не всё поле, а только ячейки которые были использованны
 */
@Getter
public class CellOutput {
    private final ArrayList<Integer> x = new ArrayList<>();
    private final ArrayList<Integer> y = new ArrayList<>();
    private final ArrayList<Integer> columnRes = new ArrayList<>();
}
