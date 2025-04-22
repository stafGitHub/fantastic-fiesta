package ru.shift.model.events.fields;

import ru.shift.model.dto.Cell;
import ru.shift.model.events.GameEvent;

import java.util.List;

public record UpdateTheCell(List<Cell> cellOutput) implements GameEvent {
}
