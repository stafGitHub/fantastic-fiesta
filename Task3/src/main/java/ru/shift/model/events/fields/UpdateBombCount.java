package ru.shift.model.events.fields;

import ru.shift.events.GameEvent;

public record UpdateBombCount(int count) implements GameEvent {
}
