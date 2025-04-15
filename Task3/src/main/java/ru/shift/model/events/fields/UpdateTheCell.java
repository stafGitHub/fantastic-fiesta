package ru.shift.model.events.fields;

import ru.shift.model.dto.PlayingFieldCells;
import ru.shift.model.events.GameEvent;

public record UpdateTheCell(PlayingFieldCells playingFieldCells) implements GameEvent {
}
