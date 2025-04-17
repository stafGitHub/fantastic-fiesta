package ru.shift.events.fields;

import ru.shift.events.GameEvent;
import ru.shift.model.dto.CellOutput;

public record UpdateTheCell(CellOutput cellOutput) implements GameEvent {
}
