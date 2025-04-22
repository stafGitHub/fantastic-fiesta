package ru.shift.model.events;

import ru.shift.events.GameEvent;
import ru.shift.model.dto.Cell;

import java.util.List;

public record UpdateTheCell(List<Cell> cellOutput) implements GameEvent {
}
