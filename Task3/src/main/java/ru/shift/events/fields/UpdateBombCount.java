package ru.shift.events.fields;

import ru.shift.events.GameEvent;

public record UpdateBombCount(int count) implements GameEvent {
}
