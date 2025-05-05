package ru.shift.timer.events;

import ru.shift.model.events.GameEvent;

public record TimeUpdate(int time) implements GameEvent {
}
