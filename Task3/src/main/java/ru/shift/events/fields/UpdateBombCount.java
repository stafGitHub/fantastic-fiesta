package ru.shift.model.events.fields;

import ru.shift.model.events.GameEvent;

public record UpdateBombCount(int count) implements GameEvent {
}
