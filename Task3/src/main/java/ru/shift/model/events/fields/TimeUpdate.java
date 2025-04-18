package ru.shift.model.events.fields;

import ru.shift.events.GameEvent;

public record TimeUpdate(int time) implements GameEvent {
}
