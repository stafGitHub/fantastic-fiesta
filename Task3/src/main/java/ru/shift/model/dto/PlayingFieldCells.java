package ru.shift.model.dto;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class PlayingFieldCells {
    private final ArrayList<Integer> x = new ArrayList<>();
    private final ArrayList<Integer> y = new ArrayList<>();
    private final ArrayList<Integer> columnRes = new ArrayList<>();
}
