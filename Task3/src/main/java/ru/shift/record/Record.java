package ru.shift.record;

import java.io.Serializable;

public record Record(Integer time , String name) implements Serializable {
}