package ru.shift.model.events.record;

import ru.shift.model.events.GameEvent;

public record NewRecord(int time) implements GameEvent {
}
