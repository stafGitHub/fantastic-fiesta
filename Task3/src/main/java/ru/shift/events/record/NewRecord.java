package ru.shift.events.record;

import ru.shift.events.GameEvent;

public record NewRecord(int time) implements GameEvent {
}
