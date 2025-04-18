package ru.shift.record.events;

import ru.shift.events.GameEvent;

public record NewRecord(int time) implements GameEvent {
}
