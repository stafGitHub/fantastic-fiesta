package ru.shift.model.events.fields;

import ru.shift.model.dto.CellOutput;
import ru.shift.model.events.GameEvent;

import java.util.List;

public record UpdateTheCell(List<CellOutput> cellOutput) implements GameEvent {
}
