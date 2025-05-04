package ru.shift.record;

import java.io.Serializable;

public record GameRecord(Integer time, String name) implements Serializable {
}