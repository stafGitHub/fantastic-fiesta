package ru.shift.record.events;

import ru.shift.model.events.GameEvent;

public record NewRecord(int time) implements GameEvent {
}
