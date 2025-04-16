package ru.shift.events.fields;

import ru.shift.model.dto.PlayingFieldCells;
import ru.shift.events.GameEvent;

public record UpdateTheCell(PlayingFieldCells playingFieldCells) implements GameEvent {
}
