package ru.shift.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class Cell {
    public static final int MINE = -1;
    public static final int EMPTY_COLUMN = 0;
    private final int x;
    private final int y;
    @Setter
    private int meaning = 0;
    @Setter
    private boolean flag = false;
    @Setter
    private boolean open = false;
}
