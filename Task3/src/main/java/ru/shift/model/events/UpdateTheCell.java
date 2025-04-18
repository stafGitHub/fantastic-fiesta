package ru.shift.model.events;

import ru.shift.events.GameEvent;
import ru.shift.model.dto.CellOutput;

import java.util.List;

public record UpdateTheCell(List<CellOutput> cellOutput) implements GameEvent {
}
